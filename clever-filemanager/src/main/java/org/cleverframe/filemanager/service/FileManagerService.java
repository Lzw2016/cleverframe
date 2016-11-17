package org.cleverframe.filemanager.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.dao.FileInfoDao;
import org.cleverframe.filemanager.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 22:13 <br/>
 */
@Service(FilemanagerBeanNames.FileManagerService)
public class FileManagerService extends BaseService {

    @Autowired
    @Qualifier(FilemanagerBeanNames.FileInfoDao)
    private FileInfoDao fileInfoDao;

    /**
     * 分页查询上传文件信息<br>
     *
     * @param page      分页数据
     * @param digest    查询参数：文件签名
     * @param fileName  查询参数：文件名称
     * @param newName   查询参数：服务器端文件名
     * @param startTime 查询参数：开始时间
     * @param endTime   查询参数：结束时间
     */
    public Page<FileInfo> findFileInfoByPage(Page<FileInfo> page, String digest, String fileName, String newName, Date startTime, Date endTime) {
        fileInfoDao.findFileInfoByPage(page, digest, fileName, newName, startTime, endTime);
        return page;
    }
}
