package org.cleverframe.filemanager.utils;

import org.apache.commons.lang3.Validate;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.fastdfs.client.StorageClient;
import org.cleverframe.fastdfs.model.FileInfo;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadCallback;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/22 21:52 <br/>
 */
public class FastDfsUtils {
//    /**
//     * 日志对象
//     */
//    private final static Logger logger = LoggerFactory.getLogger(FastDfsUtils.class);

//    private static TrackerClient trackerClient;

    private static StorageClient storageClient;

    static {
//        trackerClient = SpringContextHolder.getBean(SpringBeanNames.FastDfsTrackerClient);
//        if (trackerClient == null) {
//            throw new RuntimeException("### Bean注入失败， BeanName=" + SpringBeanNames.FastDfsTrackerClient);
//        }
        storageClient = SpringContextHolder.getBean(SpringBeanNames.FastDfsStorageClient);
        if (storageClient == null) {
            throw new RuntimeException("### Bean注入失败， BeanName=" + SpringBeanNames.FastDfsStorageClient);
        }
    }

    /**
     * 上传文件到FastDFS服务器
     *
     * @param inputStream 文件输入流，注意需要你自己关闭
     * @param fileSize    文件大小(一定要准确)
     * @param fileExtName 文件扩展名(如：jpg),不包含“.”
     * @return 文件存储路径
     */
    public static StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName) {
        Validate.isTrue(fileSize > 0, "文件大小不能小于等于0");
        return storageClient.uploadFile(inputStream, fileSize, fileExtName, null);
    }

    /**
     * 从FastDFS下载文件，
     *
     * @param groupName        FastDFS组名称(如：group1)
     * @param remoteFilename   文件名(如：M00/00/00/wKhuZVX-auOAAcgBAACizgxzYlg393.jpg)
     * @param outputStream     文件输出流
     * @param downloadCallback 下载文件回调方法
     * @return 成功返回true
     */
    public static Boolean downloadFile(String groupName, String remoteFilename, OutputStream outputStream, DownloadCallback<Boolean> downloadCallback) {
        return storageClient.downloadFile(groupName, remoteFilename, downloadCallback);
    }

    /**
     * 删除FastDFS服务器里的文件
     *
     * @param groupName      FastDFS组名称(如：group1)
     * @param remoteFilename 文件名(如：M00/00/00/wKhuZVX-auOAAcgBAACizgxzYlg393.jpg)
     * @return 成功返回true
     */
    public static Boolean deleteFile(String groupName, String remoteFilename) {
        return storageClient.deleteFile(groupName, remoteFilename);
    }

    /**
     * 判断文件是否存在
     *
     * @param groupName      FastDFS组名称(如：group1)
     * @param remoteFilename 文件名(如：M00/00/00/wKhuZVX-auOAAcgBAACizgxzYlg393.jpg)
     * @return 文件存在返回true，失败返回false
     */
    public static boolean existsFile(String groupName, String remoteFilename) {
        FileInfo fileInfo = storageClient.queryFileInfo(groupName, remoteFilename);
        return fileInfo != null;
    }

    /**
     * 获取文件信息
     *
     * @param groupName      FastDFS组名称(如：group1)
     * @param remoteFilename 文件名(如：M00/00/00/wKhuZVX-auOAAcgBAACizgxzYlg393.jpg)
     * @return 不存在返回null
     */
    public static FileInfo getFileInfo(String groupName, String remoteFilename) {
        return storageClient.queryFileInfo(groupName, remoteFilename);
    }
}
