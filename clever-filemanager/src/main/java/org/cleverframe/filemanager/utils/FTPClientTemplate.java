package org.cleverframe.filemanager.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.cleverframe.common.configuration.FilemanagerConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * FTP客户端工具，基于commons.net
 * <b>注意：使用完毕一定要关闭连接</b>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/19 14:11 <br/>
 */
public class FTPClientTemplate implements Closeable {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FTPClientTemplate.class);

    /**
     * 存储上传文件的FTP服务器地址
     */
    public final static String FTP_HOST;
    /**
     * 存储上传文件的FTP服务器端口号
     */
    public final static String FTP_PORT;
    /**
     * 存储上传文件的FTP服务器用户名
     */
    public final static String FTP_USER_NAME;
    /**
     * 存储上传文件的FTP服务器用户密码
     */
    public final static String FTP_PASSWORD;

    static {
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        if (config == null) {
            throw new RuntimeException("Spring Bean注入失败, BeanName=" + SpringBeanNames.Config);
        }
        FTP_HOST = config.getConfig(FilemanagerConfigNames.FTP_HOST);
        FTP_PORT = config.getConfig(FilemanagerConfigNames.FTP_PORT);
        FTP_USER_NAME = config.getConfig(FilemanagerConfigNames.FTP_USERNAME);
        FTP_PASSWORD = config.getConfig(FilemanagerConfigNames.FTP_PASSWORD);
        if (logger.isDebugEnabled()) {
            String tmp = "\r\n" +
                    "#=======================================================================================================================#\r\n" +
                    "# 连接文件服务器FTP默认配置：\r\n" +
                    "#\t FTP HOST：" + FTP_HOST + "\r\n" +
                    "#\t FTP PORT：" + FTP_PORT + "\r\n" +
                    "#\t FTP USER NAME：" + FTP_USER_NAME + "\r\n" +
                    "#\t FTP PASSWORD：" + FTP_PASSWORD + "\r\n" +
                    "#=======================================================================================================================#\r\n";
            logger.debug(tmp);
        }
    }

    /**
     * apache.commons.net实现的FTPClient
     */
    private FTPClient ftpclient;

    /**
     * 连接到配置文件中指定的FTP服务器上<br>
     */
    public FTPClientTemplate() throws IOException {
        this(FTP_HOST, Integer.parseInt(FTP_PORT), FTP_USER_NAME, FTP_PASSWORD);
    }

    /**
     * 连接到指定的FTP服务器上<br>
     *
     * @param host     主机地址
     * @param port     端口号
     * @param username 用户名
     * @param password 密码
     */
    public FTPClientTemplate(String host, int port, String username, String password) throws IOException {
        ftpclient = new FTPClient();
        try {
            ftpclient.connect(host, port);
            int reply = ftpclient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpclient.disconnect();
                logger.warn("FTP服务器拒绝连接");
            }
            if (username != null) {
                if (!ftpclient.login(username, password)) {
                    ftpclient.disconnect();
                    logger.warn("登陆验证失败，请检查账号和密码是否正确");
                }
            }
        } catch (SocketException e) {
            logger.error("登陆验证失败，请检查账号和密码是否正确", e);
            throw e;
        } catch (IOException e) {
            logger.error("无法用指定用户名和密码连接至指定FTP服务器，请检查是否连接数限制", e);
            throw e;
        }
    }

    /**
     * 创建基于根目录的目录(支持中文目录)，如：/work/Java/web目录<br>
     * <b>注意：操作成功之后，工作目录在创建目录下！</b>
     *
     * @param path 要创建的目录路径，不能含有特殊字符，如 ：\、/?"<>等
     * @return 如果成功创建目录返回true，否则返回false
     */
    public boolean mkdir(String path) throws IOException {
        // 文件路径转换成Unix形式
        path = FilenameUtils.separatorsToUnix(path);
        // 验证文件路径
        if (StringUtils.isBlank(path)) {
            return false;
        }
        String[] pathArray = StringUtils.split(path, '/');
        // 跳到根目录
        if (!ftpclient.changeWorkingDirectory("/")) {
            return false;
        }
        for (int i = 0; i < pathArray.length; i++) {
            // 支持中文目录
            pathArray[i] = new String(pathArray[i].getBytes(), ftpclient.getControlEncoding());
            if (!ftpclient.changeWorkingDirectory(pathArray[i])) {
                if (!ftpclient.makeDirectory(pathArray[i])) {
                    return false;
                }
                if (!ftpclient.changeWorkingDirectory(pathArray[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 改变工作目录，基于根目录的目录路径<br>
     *
     * @param path 目录路径(支持中文目录)，不能含有特殊字符，如 ：\、/?"<>等
     * @return 成功返回true，失败返回false
     */
    public boolean changeWorkingDirectory(String path) throws IOException {
        // 文件路径转换成Unix形式
        path = FilenameUtils.separatorsToUnix(path);
        // 验证文件路径
        if (StringUtils.isBlank(path)) {
            return false;
        }
        String[] pathArray = StringUtils.split(path, '/');
        // 跳到根目录
        if (!ftpclient.changeWorkingDirectory("/")) {
            return false;
        }
        for (int i = 0; i < pathArray.length; i++) {
            // 支持中文目录
            pathArray[i] = new String(pathArray[i].getBytes(), ftpclient.getControlEncoding());
            if (!ftpclient.changeWorkingDirectory(pathArray[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 上传文件到FTP服务器，目录不存在会创建目录<br>
     * <b>注意：操作成功之后，工作目录在上传文件目录下！</b>
     *
     * @param path        文件全路径(支持中文目录)，如：/Work/Java/Web/Ftp.pdf
     * @param inputStream 上传文件输入流
     * @return 成功返回true，失败返回false
     */
    public boolean uploadFile(String path, InputStream inputStream) throws IOException {
        ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
        // 得到文件名
        String fileName = FilenameUtils.getName(path);
        if (StringUtils.isBlank(fileName)) {
            return false;
        }
        // 得到路径部分
        String pathStr = FilenameUtils.getFullPath(path);
        return mkdir(pathStr) && ftpclient.storeFile(new String(fileName.getBytes(), ftpclient.getControlEncoding()), inputStream);
    }

    /**
     * 下载FTP服务器文件<br>
     * <b>注意：操作成功之后，工作目录在下载文件目录下！</b>
     *
     * @param path 文件全路径(支持中文目录)，如：/Work/Java/Web/Ftp.pdf
     * @return 文件输出流(读取完毕后必须要关闭)，失败返回null
     */
    public InputStream downloadFile(String path) throws IOException {
        ftpclient.setFileType(FTP.BINARY_FILE_TYPE);
        // 得到文件名
        String fileName = FilenameUtils.getName(path);
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        // 得到路径部分
        String pathStr = FilenameUtils.getFullPath(path);
        if (!changeWorkingDirectory(pathStr)) {
            return null;
        }
        return ftpclient.retrieveFileStream(new String(fileName.getBytes(), ftpclient.getControlEncoding()));
    }

    /**
     * 只删除文件,如果删除空目录请用如下方法： <br>
     * <code>
     * getFtpclient().removeDirectory(String pathname)
     * </code> <br>
     * 参考  {@link org.apache.commons.net.ftp.FTPClient FTPClient}
     *
     * @param path 文件全路径(支持中文目录)，如：/Work/Java/Web/Ftp.pdf
     * @return 成功删除返回true，否则返回false(如果文件不存在也返回false)
     */
    public boolean deleteFile(String path) throws IOException {
        // 得到文件名
        String fileName = FilenameUtils.getName(path);
        if (StringUtils.isBlank(fileName)) {
            throw new IOException("文件名不能为空：" + path);
        }
        // 得到路径部分
        String pathStr = FilenameUtils.getFullPath(path);
        if (!changeWorkingDirectory(pathStr)) {
            throw new IOException("操作失败：changeWorkingDirectory");
        }
        return ftpclient.deleteFile(new String(fileName.getBytes(), ftpclient.getControlEncoding()));
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件全路径(支持中文目录)，如：/Work/Java/Web/Ftp.pdf
     * @return 存在返回true，不存在返回false
     */
    public boolean existsFile(String path) throws IOException {
        FTPFile[] files = ftpclient.listFiles(new String(path.getBytes(), ftpclient.getControlEncoding()));
        return files.length >= 1;
    }

    /**
     * 关闭FTP连接
     */
    @Override
    public void close() throws IOException {
        this.ftpclient.disconnect();
    }

    public FTPClient getFtpclient() {
        return ftpclient;
    }
}
