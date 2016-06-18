package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.Dict;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 21:22 <br/>
 */
@Repository(CoreBeanNames.DictDao)
public class DictDao extends BaseDao<Dict> {
    /**
     * 获取数据字典，使用分页<br/>
     *
     * @param page     分页对象
     * @param dictKey  查询参数：字典键
     * @param dictType 查询参数：字典分类
     * @param id       查询参数：ID
     * @param uuid     查询参数：UUID
     * @param delFlag  查询参数：删除标记
     * @return 分页数据
     */
    public Page<Dict> findByPage(Page<Dict> page, String dictKey, String dictType, Long id, String uuid, Character delFlag) {
        Parameter param = new Parameter(delFlag);
        param.put("dictKey", dictKey);
        param.put("dictType", dictType);
        param.put("id", id);
        param.put("uuid", uuid);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.DictDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }

    /**
     * 根据字典类型获取数据字典集合
     *
     * @param dictType 查询参数：字典分类
     * @return 数据字典集合
     */
    public List<Dict> findByType(String dictType) {
        Parameter param = new Parameter(Dict.DEL_FLAG_NORMAL);
        param.put("dictType", dictType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.DictDao.findByType");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 查询所有类型的数据字典
     *
     * @return 数据字典集合
     */
    public List<Dict> findAllType() {
        Parameter param = new Parameter(Dict.DEL_FLAG_NORMAL);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.DictDao.findAllType");
        return hibernateDao.findBySql(sql, param);
    }

    /**
     * 根据字典类型删除所有数据字典，直接从数据库删除
     *
     * @param dictType 查询参数：字典分类
     * @return 成功返回true，失败返回false
     */
    public boolean deleteByType(String dictType) {
        Parameter param = new Parameter();
        param.put("dictType", dictType);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.DictDao.deleteByType");
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(sql, param);
        sqlQuery.executeUpdate();
        return true;
    }
}
