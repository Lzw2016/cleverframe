package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.QLScript;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据库脚本Dao<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 10:58 <br/>
 */
@Repository(CoreBeanNames.QLScriptDao)
public class QLScriptDao extends BaseDao<QLScript> {
    /**
     * 根据name查询QLScript，没有按公司隔离数据，参数: <br/>
     * 1.:delFlag delFlag 删除标识<br/>
     * 2.:name name 脚本名称<br/>
     */
    public static final String SQL_GET_QLSCRIPT_BY_NAME = "SELECT * FROM core_qlscript WHERE del_flag=:delFlag AND name=:name";

    /**
     * 查询QLScript，没有按公司隔离数据，参数:<br/>
     * 1.:delFlag delFlag 删除标识<br/>
     * 2.:name name 脚本名称<br/>
     * 3.:script_type scriptType 脚本类型<br/>
     * 4.:id id 脚本ID<br/>
     * 5.:uuid uuid 数据UUID<br/>
     */
    public static final String SQL_FIND_ALL_QLSCRIPT = "SELECT * FROM core_qlscript WHERE del_flag=:delFlag "
            + " AND (name LIKE :name OR :name = '' ) "
            + " AND (script_type=:scriptType OR :scriptType='' ) "
            + " AND (id=:id OR :id=-1) "
            + " AND (uuid=:uuid OR :uuid='') "
            + " ORDER BY name ";

    /**
     * 直接从数据库删除SQL脚本，参数 name：数据库脚本名称
     */
    public static final String DELETE_QLSCRIPT_BY_NAME = "DELETE FROM core_qlscript WHERE name = :name";

    /**
     * 根据脚本名称获取脚本对象，只获取没有被软删除的数据<br/>
     *
     * @param name 查询参数：脚本名称(使用包名称+类名+方法名)
     */
    public QLScript getQLScriptByname(String name) {
        Parameter param = new Parameter(QLScript.DEL_FLAG_NORMAL);
        param.put("name", name);
        return hibernateDao.getBySql(SQL_GET_QLSCRIPT_BY_NAME, param);
    }

    /**
     * 得到所有数据库脚本，不包含软删除的数据
     */
    public List<QLScript> findAllScript() {
        Parameter param = new Parameter(QLScript.DEL_FLAG_NORMAL);
        param.put("name", "");
        param.put("scriptType", "");
        param.put("id", -1L);
        param.put("uuid", "");
        return hibernateDao.findBySql(SQL_FIND_ALL_QLSCRIPT, param);
    }

    /**
     * 获取所有的数据库脚本，使用分页
     *
     * @param page       分页对象
     * @param name       查询参数：脚本名称(使用包名称+类名+方法名)
     * @param scriptType 查询参数：脚本类型
     * @param id         查询参数：脚本ID
     * @param uuid       查询参数：UUID
     * @param delFlag    查询参数：删除标记
     * @return 分页数据
     */
    public Page<QLScript> findQLScriptByPage(Page<QLScript> page, String name, String scriptType, Long id, String uuid, Character delFlag) {
        Parameter param = new Parameter(delFlag);
        param.put("name", name);
        param.put("scriptType", scriptType);
        param.put("id", id);
        param.put("uuid", uuid);
        return hibernateDao.findBySql(page, SQL_FIND_ALL_QLSCRIPT, param);
    }

    /**
     * 直接删除数据库脚本，也会从QLScript缓存中删除<br/>
     *
     * @param name 脚本名称(使用包名称+类名+方法名)
     * @return 成功返回true，失败返回false
     */
    public boolean deleteQLScript(String name) {
        Parameter param = new Parameter();
        param.put("name", name);
        SQLQuery sqlQuery = hibernateDao.createSqlQuery(DELETE_QLSCRIPT_BY_NAME, param);
        sqlQuery.executeUpdate();
        return true;
    }
}
