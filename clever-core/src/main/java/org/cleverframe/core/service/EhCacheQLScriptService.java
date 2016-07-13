package org.cleverframe.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.QLScriptDao;
import org.cleverframe.core.entity.QLScript;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据库脚本Service，使用EhCache来缓存数据库脚本<br/>
 * <b>注意：不会缓存软删除了的数据</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 10:55 <br/>
 */
@Service(CoreBeanNames.EhCacheQLScriptService)
public class EhCacheQLScriptService extends BaseService implements IQLScriptService {
    @Autowired
    @Qualifier(CoreBeanNames.QLScriptDao)
    private QLScriptDao qLScriptDao;

    /**
     * 数据库脚本缓存<br/>
     * <b>注意：不缓存软删除了的数据</b>
     */
    private Cache qLScriptCahe = EhCacheUtils.createCache(EhCacheNames.QLScriptCache);

    /**
     * 根据脚本名称获取脚本对象，优先到缓存中获取<br/>
     * 1.到QLScript缓存中获取<br/>
     * 2.QLScript缓存中没有才到数据库里获取(只获取未软删除的数据)并存到缓存中<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 数据库脚本名称(使用包名称+类名+方法名)
     * @return 数据库脚本信息(QLScript), 不存在返回null
     */
    @Override
    public QLScript getQLScriptByName(String name) {
        QLScript qLScript = null;
        Element element = qLScriptCahe.get(name);
        if (null != element && element.getObjectValue() instanceof QLScript) {
            qLScript = (QLScript) element.getObjectValue();
        }
        if (qLScript == null) {
            qLScript = qLScriptDao.getQLScriptByname(name);
            if (qLScript != null) {
                element = new Element(name, qLScript);
                qLScriptCahe.put(element);
            }
        }
        return qLScript;
    }

    /**
     * 保存数据库脚本，也会保存到QLScript缓存中(只缓存状态正常的数据)<br/>
     *
     * @param qLScript 数据库脚本对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean saveQLScript(QLScript qLScript) {
        if (qLScript != null) {
            qLScriptDao.getHibernateDao().save(qLScript);
            // 只缓存状态正常的数据
            if (qLScript.getDelFlag() != null && QLScript.DEL_FLAG_NORMAL == qLScript.getDelFlag()) {
                Element element = new Element(qLScript.getName(), qLScript);
                qLScriptCahe.put(element);
            }
            return true;
        }
        return false;
    }

    /**
     * 更新数据库脚本，也会刷新QLScript缓存数据(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param qLScript 数据库脚本信息(QLScript)
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateQLScript(QLScript qLScript) {
        if (qLScript != null) {
            qLScriptDao.getHibernateDao().update(qLScript, false, true);
            // 只缓存状态正常的数据
            if (qLScript.getDelFlag() != null && QLScript.DEL_FLAG_NORMAL == qLScript.getDelFlag()) {
                Element element = new Element(qLScript.getName(), qLScript);
                qLScriptCahe.put(element);
            } else {
                qLScriptCahe.remove(qLScript.getName());
            }
            return true;
        }
        return false;
    }

    /**
     * 直接删除数据库脚本，也会从QLScript缓存中删除<br/>
     * <b>注意：软删除使用{@link #updateQLScript(QLScript)}</b>
     *
     * @param name 脚本名称(使用包名称+类名+方法名)
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteQLScript(String name) {
        qLScriptCahe.remove(name);
        return qLScriptDao.deleteQLScript(name);
    }

    /**
     * 获取数据库脚本(只获取没有被软删除的数据)，直接到数据库里获取，并覆盖到QLScript缓存<br/>
     * 1.先根据 name 清除缓存数据<br/>
     * 2.再根据 name 从数据库获取脚本(只获取没有被软删除的数据)，并覆盖到QLScript缓存<br/>
     *
     * @param name 脚本名称(使用包名称+类名+方法名)
     * @return 返回新的QLScript，否则返回null
     */
    @Override
    public QLScript refreshQLScript(String name) {
        qLScriptCahe.remove(name);
        QLScript qLScript = qLScriptDao.getQLScriptByname(name);
        if (qLScript != null && QLScript.DEL_FLAG_NORMAL.equals(qLScript.getDelFlag())) {
            Element element = new Element(qLScript.getName(), qLScript);
            qLScriptCahe.put(element);
        } else {
            qLScriptCahe.remove(name);
        }
        return qLScript;
    }

    /**
     * 从数据库查询所有的QL脚本，并存到QLScript缓存中(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     * 1.先清空缓存<br/>
     * 2.在从数据库查询所有的QLScript(不包含软删除的数据)，添加到缓存中<br/>
     *
     * @return 所有数据库脚本(不包含软删除的数据)
     */
    @Override
    public List<QLScript> findAllQLScript() {
        // 先清空缓存
        qLScriptCahe.removeAll();
        // 在从数据库查询所有的QLScript(不包含软删除的数据)，添加到缓存中
        List<QLScript> list = qLScriptDao.findAllScript();
        for (QLScript script : list) {
            if (qLScriptCahe.get(script.getName()) == null) {
                Element element = new Element(script.getName(), script);
                qLScriptCahe.put(element);
            }
        }
        return list;
    }

    /**
     * 获取数据库脚本，使用分页，同时把查询到的数据放入QLScript缓存(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param page       分页对象
     * @param name       查询参数：脚本名称(使用包名称+类名+方法名)
     * @param scriptType 查询参数：脚本类型
     * @param id         查询参数：脚本ID
     * @param uuid       查询参数：UUID
     * @param delFlag    查询参数：删除标记
     * @return 分页数据
     */
    @Override
    public Page<QLScript> findAllQLScript(Page<QLScript> page, String name, String scriptType, Long id, String uuid, Character delFlag) {
        page = qLScriptDao.findQLScriptByPage(page, name, scriptType, id, uuid, delFlag);
        for (QLScript script : page.getList()) {
            qLScriptCahe.remove(script.getName());
            // 只缓存状态正常的数据
            if (script.getDelFlag() != null && QLScript.DEL_FLAG_NORMAL == script.getDelFlag()) {
                Element element = new Element(script.getName(), script);
                qLScriptCahe.put(element);
            }
        }
        return page;
    }
}
