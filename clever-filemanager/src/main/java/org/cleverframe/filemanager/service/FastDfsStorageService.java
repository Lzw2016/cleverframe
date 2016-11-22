package org.cleverframe.filemanager.service;

import com.google.common.util.concurrent.RateLimiter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.fastdfs.model.StorePath;
import org.cleverframe.fastdfs.protocol.storage.callback.DownloadCallback;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.dao.FileInfoDao;
import org.cleverframe.filemanager.entity.FileInfo;
import org.cleverframe.filemanager.utils.FastDfsUtils;
import org.cleverframe.filemanager.utils.FileDigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/22 10:50 <br/>
 */
@Service(FilemanagerBeanNames.FastDfsStorageService)
public class FastDfsStorageService extends BaseService implements IStorageService {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FastDfsStorageService.class);

    @Autowired
    @Qualifier(FilemanagerBeanNames.FileInfoDao)
    private FileInfoDao fileInfoDao;

    @Override
    public FileInfo lazySaveFile(String fileName, String fileDigest, Character digestType) throws Exception {
        // 此文件是否已经上传过了，不需要重复保存
        FileInfo dbFileInfo;
        if (StringUtils.isBlank(fileDigest) || digestType == null) {
            return null;
        }
        // 到数据库查找判断此文件是否已经上传过了
        dbFileInfo = fileInfoDao.findFileInfoByDigest(fileDigest, digestType);
        if (dbFileInfo == null || StringUtils.isBlank(dbFileInfo.getFilePath()) || StringUtils.isBlank(dbFileInfo.getNewName())) {
            return null;
        } else {
            if (!FastDfsUtils.existsFile(dbFileInfo.getFilePath(), dbFileInfo.getNewName())) {
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
        // 上传文件的存储类型：FastDFS服务器
        fileInfo.setStoredType(FileInfo.FASTDFS_STORAGE);
        // 计算文件的绝对路径，保存文件
        long storageStart = System.currentTimeMillis();
        inputStream = multipartFile.getInputStream();
        StorePath storePath = null;
        try {
            storePath = FastDfsUtils.uploadFile(inputStream, multipartFile.getSize(), FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        } finally {
            inputStream.close();
        }
        long storageEnd = System.currentTimeMillis();
        // group name
        fileInfo.setFilePath(storePath.getGroup());
        // file name
        fileInfo.setNewName(storePath.getPath());
        // 设置存储所用的时间
        fileInfo.setStoredTime(storageEnd - storageStart);
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
        logger.info("[FastDFS服务器]删除文件引用数量：{} 条", count);
        if (lazy) {
            // lazy == true:只删除FileInfo
            return 2;
        }
        Boolean success = FastDfsUtils.deleteFile(fileInfo.getFilePath(), fileInfo.getNewName());
        if (!success) {
            logger.error("[FastDFS服务器]到FastDFS服务器删除文件失败！");
            throw new Exception("[FastDFS服务器]到FastDFS服务器删除文件失败！");
        }
        return 1;
    }

    @Override
    public FileInfo isExists(Serializable fileInfoUuid) throws Exception {
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo == null) {
            return null;
        }
        if (FastDfsUtils.existsFile(fileInfo.getFilePath(), fileInfo.getNewName())) {
            return fileInfo;
        }
        return null;
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public FileInfo openFile(Serializable fileInfoUuid, OutputStream outputStream) throws Exception {
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo != null) {
            Boolean success = FastDfsUtils.downloadFile(fileInfo.getFilePath(), fileInfo.getNewName(), outputStream, new DownloadCallback<Boolean>() {
                @Override
                public Boolean receive(InputStream inputStream) throws IOException {
                    if (inputStream == null) {
                        return Boolean.FALSE;
                    }
                    try {
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
                        return Boolean.TRUE;
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }
            });
            if (!success) {
                logger.error("[FastDFS服务器]到FastDFS服务器下载文件失败！");
                throw new Exception("[FastDFS服务器]到FastDFS服务器下载文件失败！");
            }
            return fileInfo;
        }
        return null;
    }

    @SuppressWarnings("Convert2Lambda")
    @Override
    public FileInfo openFileSpeedLimit(Serializable fileInfoUuid, OutputStream outputStream, long maxSpeed) throws Exception {
        if (maxSpeed <= 0) {
            maxSpeed = Max_Open_Speed;
        }
        RateLimiter rateLimiter = RateLimiter.create(maxSpeed);
        FileInfo fileInfo = fileInfoDao.getFileInfoByUuid(fileInfoUuid);
        if (fileInfo != null) {
            Boolean success = FastDfsUtils.downloadFile(fileInfo.getFilePath(), fileInfo.getNewName(), outputStream, new DownloadCallback<Boolean>() {
                @Override
                public Boolean receive(InputStream inputStream) throws IOException {
                    if (inputStream == null) {
                        return Boolean.FALSE;
                    }
                    byte[] data = new byte[32 * 1024];
                    int readByte;
                    double sleepTime;
                    try {
                        while (true) {
                            readByte = inputStream.read(data);
                            if (readByte <= 0) {
                                break;
                            }
                            outputStream.write(data, 0, readByte);
                            sleepTime = rateLimiter.acquire(readByte);
                            logger.debug("[FastDFS服务器]打开文件UUID:[{}], 读取字节数:[{}], 休眠时间:[{}]秒", fileInfo.getUuid(), readByte, sleepTime);
                        }
                        outputStream.flush();
                        return Boolean.TRUE;
                    } finally {
                        IOUtils.closeQuietly(inputStream);
                    }
                }
            });
            if (!success) {
                logger.error("[FastDFS服务器]到FastDFS服务器下载文件失败！");
                throw new Exception("[FastDFS服务器]到FastDFS服务器下载文件失败！");
            }
            return fileInfo;
        }
        return null;
    }
}
