package org.cleverframe.common.configuration;

/**
 * 数据库配置<br/>
 * cleverframe-fileupload.properties 配置文件里的配置键名称在此类都需要定义相应的字符串常量<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/12 12:17 <br/>
 *
 * @see FilemanagerConfigValues
 */
public class FilemanagerConfigNames {

    /**
     * 最大文件上传限制，单位字节. 10M=10*1024*1024(B)=10485760 bytes
     */
    public static final String MAX_UPLOAD_SIZE = "fileupload.web.maxUploadSize";

    /**
     * 上传文件的存储路径，当storedType=1时使用（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public static final String FILE_STORAGE_PATH = "fileupload.web.fileStoragePath";

    /**
     * 上传文件到FTP的存储路径，当storedType=2时使用（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public static final String FILE_STORAGE_PATH_BY_FTP = "fileupload.web.fileStoragePathByFTP";

    /**
     * 上传文件到FastDFS服务器，当storedType=3时使用（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public static final String FASTDFS_CONF_FILENAME = "fileupload.FastDFS.conf.filename";

    /**
     * FTP服务器IP
     */
    public static final String FTP_HOST = "fileupload.FTP.host";

    /**
     * FTP服务器端口
     */
    public static final String FTP_PORT = "fileupload.FTP.port";

    /**
     * FTP服务器用户名
     */
    public static final String FTP_USERNAME = "fileupload.FTP.username";

    /**
     * FTP服务器密码
     */
    public static final String FTP_PASSWORD = "fileupload.FTP.password";
}
