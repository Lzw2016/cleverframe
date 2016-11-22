package org.cleverframe.filemanager.service;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.configuration.FilemanagerConfigNames;
import org.cleverframe.common.configuration.IConfig;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.IDCreateUtils;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.dao.FileInfoDao;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.utils.FTPClientTemplate;
import org.cleverframe.filemanager.utils.FileDigestUtils;
import org.cleverframe.filemanager.utils.StoragePathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/19 13:47 <br/>
 */
@Service(FilemanagerBeanNames.FtpStorageService)
public class FtpStorageService extends BaseService implements IStorageService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FtpStorageService.class);

    /**
     * 上传文件到FTP的存储路径
     * <b>注意：路径后面没有多余的“\”或者“/”</b>
     */
    public static final String FILE_STORAGE_PATH_BY_FTP;

    static {
        IConfig config = SpringContextHolder.getBean(SpringBeanNames.Config);
        if (config == null) {
            throw new RuntimeException("[FTP服务器]Spring Bean注入失败, BeanName=" + SpringBeanNames.Config);
        }
        String path = config.getConfig(FilemanagerConfigNames.FILE_STORAGE_PATH_BY_FTP);
        FILE_STORAGE_PATH_BY_FTP = FilenameUtils.normalizeNoEndSeparator(path);
        logger.info("### [FTP服务器]上传文件存储到FTP服务器的路径地址[{}]", FILE_STORAGE_PATH_BY_FTP);
    }

    @Autowired
    @Qualifier(FilemanagerBeanNames.FileInfoDao)
    private FileInfoDao fileInfoDao;

    @Override
    public FileInfo lazySaveFile(String fileName, String fileDigest, Character digestType) throws Exception {
        FileInfo dbFileInfo;
        if (StringUtils.isBlank(fileDigest) || digestType == null) {
            return null;
        }
        // 到数据库查找判断此文件是否已经上传过了
        dbFileInfo = fileInfoDao.findFileInfoByDigest(fileDigest, digestType);
        if (dbFileInfo == null || StringUtils.isBlank(dbFileInfo.getFilePath()) || StringUtils.isBlank(dbFileInfo.getNewName())) {
            return null;
        } else {
            String filepath = FILE_STORAGE_PATH_BY_FTP + dbFileInfo.getFilePath() + File.separator + dbFileInfo.getNewName();
            filepath = FilenameUtils.separatorsToUnix(filepath);
            boolean exists;
            try (FTPClientTemplate ftp = new FTPClientTemplate()) {
                exists = ftp.existsFile(filepath);
            }
            if (!exists) {
                return null;
            }
        }
        return dbFileInfo;
    }

    @Transactional(readOnly = false)
    @Override
    public FileInfo saveFile(long uploadTime, String fileSource, MultipartFile multipartFile) throws Exception {
        // 设置文件签名类型 和 文件签名
        InputStream inputStream = multipartFile.getInputStream();
        String digest = null;
        try {
            digest = FileDigestUtils.FileDigestByMD5(inputStream);
        } finally {
            inputStream.close();
        }
        // 通过文件签名检查服务器端是否有相同文件
        FileInfo lazyFileInfo = this.lazySaveFile(multipartFile.getOriginalFilename(), digest, FileInfo.MD5_DIGEST);
        if (lazyFileInfo != null) {
            return lazyFileInfo;
        }
        // 服务器端不存在相同文件
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileSource(fileSource);
        fileInfo.setUploadTime(uploadTime);
        fileInfo.setFileName(multipartFile.getOriginalFilename());
        fileInfo.setFileSize(multipartFile.getSize());
        fileInfo.setDigest(digest);
        fileInfo.setDigestType(FileInfo.MD5_DIGEST);
        // 上传文件的存储类型：FTP服务器
        fileInfo.setStoredType(FileInfo.FTP_STORAGE);
        // 设置文件存储之后的名称：UUID + 后缀名(此操作依赖文件原名称)
        String newName = IDCreateUtils.uuid();
        String fileExtension = FilenameUtils.getExtension(fileInfo.getFileName());
        if (StringUtils.isNotBlank(fileExtension)) {
            newName = newName + "." + fileExtension.toLowerCase();
        }
        fileInfo.setNewName(newName);

        // 上传文件存到FTP服务器的路径(相对路径)
        String filePath = StoragePathUtils.createFilePathByDate("");
        fileInfo.setFilePath(filePath);
        // 计算文件的绝对路径，保存文件
        String absoluteFilePath = FILE_STORAGE_PATH_BY_FTP + filePath + File.separator + newName;
        absoluteFilePath = FilenameUtils.separatorsToUnix(absoluteFilePath);
        long storageStart = System.currentTimeMillis();
        FTPClientTemplate ftp = new FTPClientTemplate();
        boolean success = false;
        try {
            inputStream = multipartFile.getInputStream();
            success = ftp.uploadFile(absoluteFilePath, inputStream);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            ftp.close();
        }
        long storageEnd = System.currentTimeMillis();
        if (!success) {
            logger.error("[FTP服务器]上传文件到FTP服务器失败！");
            throw new Exception("[FTP服务器]上传文件到FTP服务器失败！");
        }
        // 设置存储所用的时间
        fileInfo.setStoredTime(storageEnd - storageStart);
        logger.info("[FTP服务器]文件存储所用时间:[{}ms]", fileInfo.getStoredTime());
        // 保存文件信息
        fileInfoDao.getHibernateDao().save(fileInfo);
        return fileInfo;
    }

    @Transactional(readOnly = false)
    @Override
    public int deleteFile(Serializable fileInfoUuid, boolean lazy) throws Exception {
        // 1：成功删除fileInfo和服务器端文件；2：只删除了fileInfo；3：fileInfo不存在
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo == null) {
            // FileInfo 不存在或已经被删除
            return 3;
        }
        int count = fileInfoDao.deleteFileInfo(fileInfo.getFilePath(), fileInfo.getNewName());
        logger.info("[FTP服务器]删除文件引用数量：{} 条", count);
        if (lazy) {
            // lazy == true:只删除FileInfo
            return 2;
        }
        String fullPath = FILE_STORAGE_PATH_BY_FTP + fileInfo.getFilePath();
        fullPath = FilenameUtils.concat(fullPath, fileInfo.getNewName());
        fullPath = FilenameUtils.separatorsToUnix(fullPath);
        boolean success;
        try (FTPClientTemplate ftp = new FTPClientTemplate()) {
            success = ftp.deleteFile(fullPath);
        }
        if (!success) {
            logger.error("[FTP服务器]到FTP服务器删除文件失败！");
            throw new Exception("[FTP服务器]到FTP服务器删除文件失败！");
        }
        return 1;
    }

    @Override
    public FileInfo isExists(Serializable fileInfoUuid) throws Exception {
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo == null) {
            return null;
        }
        String fullPath = FILE_STORAGE_PATH_BY_FTP + fileInfo.getFilePath();
        fullPath = FilenameUtils.concat(fullPath, fileInfo.getNewName());
        fullPath = FilenameUtils.separatorsToUnix(fullPath);
        boolean exists;
        try (FTPClientTemplate ftp = new FTPClientTemplate()) {
            exists = ftp.existsFile(fullPath);
        }
        if (!exists) {
            return null;
        }
        return fileInfo;
    }

    @Override
    public FileInfo openFile(Serializable fileInfoUuid, OutputStream outputStream) throws Exception {
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo == null) {
            return null;
        }
        String fullPath = FILE_STORAGE_PATH_BY_FTP + fileInfo.getFilePath();
        fullPath = FilenameUtils.concat(fullPath, fileInfo.getNewName());
        fullPath = FilenameUtils.separatorsToUnix(fullPath);
        FTPClientTemplate ftp = new FTPClientTemplate();
        InputStream inputStream = null;
        try {
            inputStream = ftp.downloadFile(fullPath);
            if (inputStream != null) {
                byte[] data = new byte[256 * 1024];
                int readByte;
                while (true) {
                    readByte = inputStream.read(data);
                    if (readByte <= 0) {
                        break;
                    }
                    outputStream.write(data, 0, readByte);
                }
                outputStream.flush();
                return fileInfo;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            ftp.close();
        }
        return null;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public FileInfo openFileSpeedLimit(Serializable fileInfoUuid, OutputStream outputStream, long maxSpeed) throws Exception {
        if (maxSpeed <= 0) {
            maxSpeed = Max_Open_Speed;
        }
        RateLimiter rateLimiter = RateLimiter.create(maxSpeed);
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo == null) {
            return null;
        }
        String fullPath = FILE_STORAGE_PATH_BY_FTP + fileInfo.getFilePath();
        fullPath = FilenameUtils.concat(fullPath, fileInfo.getNewName());
        fullPath = FilenameUtils.separatorsToUnix(fullPath);
        FTPClientTemplate ftp = new FTPClientTemplate();
        InputStream inputStream = null;
        try {
            inputStream = ftp.downloadFile(fullPath);
            if (inputStream != null) {
                byte[] data = new byte[32 * 1024];
                int readByte;
                double sleepTime;
                while (true) {
                    readByte = inputStream.read(data);
                    if (readByte <= 0) {
                        break;
                    }
                    outputStream.write(data, 0, readByte);
                    sleepTime = rateLimiter.acquire(readByte);
                    logger.debug("[FTP服务器]打开文件UUID:[{}], 读取字节数:[{}], 休眠时间:[{}]秒", fileInfo.getUuid(), readByte, sleepTime);
                }
                outputStream.flush();
                return fileInfo;
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            ftp.close();
        }
        logger.warn("[FTP服务器]文件引用[UUID={}]对应的文件不存在", fileInfo.getUuid());
        return null;
    }
}
