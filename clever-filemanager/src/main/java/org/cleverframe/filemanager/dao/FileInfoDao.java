package org.cleverframe.filemanager.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.filemanager.FilemanagerBeanNames;
import org.cleverframe.filemanager.entity.FileInfo;
import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/17 22:03 <br/>
 */
@Repository(FilemanagerBeanNames.FileInfoDao)
public class FileInfoDao extends BaseDao<FileInfo> {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(FileInfoDao.class);

    /**
     * 根据文件签名，到数据库查找相同的文件<br>
     *
     * @param fileDigest 文件签名
     * @param digestType 文件签名类型
     * @return 上传的文件信息，未找到返回null
     */
    public FileInfo findFileInfoByDigest(String fileDigest, Character digestType) {
        Parameter param = new Parameter();
        param.put("fileDigest", fileDigest);
        param.put("digestType", digestType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.filemanager.dao.FileInfoDao.findFileInfoByDigest");
        List<FileInfo> fileInfoList = this.getHibernateDao().findBySql(sql, param);
        if (fileInfoList == null || fileInfoList.size() <= 0) {
            return null;
        } else {
            if (fileInfoList.size() > 1) {
                logger.warn("文件签名[{}]，在数据库里存在多份文件信息,数量[{}]个", fileDigest, fileInfoList.size());
            }
            return fileInfoList.get(0);
        }
    }

    /**
     * 根据UUID查询文件信息
     *
     * @param uuid 查询参数：数据UUID
     */
    public FileInfo getFileInfoByUuid(Serializable uuid) {
        Parameter param = new Parameter();
        param.put("uuid", uuid);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.filemanager.dao.FileInfoDao.getFileInfoByUuid");
        return getHibernateDao().getBySql(sql, param);
    }

    /**
     * 删除所有的文件引用
     *
     * @param filePath 文件路径
     * @param newName  文件名称
     * @return 返回删除文件引用的数量
     */
    public int deleteFileInfo(String filePath, String newName) {
        Parameter param = new Parameter();
        param.put("filePath", filePath);
        param.put("newName", newName);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.filemanager.dao.FileInfoDao.deleteFileInfo");
        Query query = this.getHibernateDao().createSqlQuery(sql, param);
        return query.executeUpdate();
    }


    // ------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * 查询同一文件在数据库里的引用数量<br>
     * <p>
     * <b>注意：文件只要存在引用就不能删除</b>
     *
     * @param filePath 文件路径
     * @param newName  服务器端的文件名
     * @return 文件被引用的数量
     */
    public int findRepeatFile(String filePath, String newName) {
        Parameter param = new Parameter();
        param.put("filePath", filePath);
        param.put("newName", newName);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.filemanager.dao.FileInfoDao.findRepeatFile");
        BigInteger count = this.getHibernateDao().getBySql(null, sql, param);
        return count.intValue();
    }

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
        Parameter param = new Parameter();
        param.put("digest", digest);
        param.put("fileName", fileName);
        param.put("newName", newName);
        param.put("startTime", startTime);
        param.put("endTime", endTime);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.filemanager.dao.FileInfoDao.findFileInfoByPage");
        this.getHibernateDao().findBySql(page, sql, param);
        return page;
    }
}
