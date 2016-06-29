package org.cleverframe.core.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.cleverframe.common.ehcache.EhCacheNames;
import org.cleverframe.common.ehcache.EhCacheUtils;
import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.TemplateDao;
import org.cleverframe.core.entity.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 模版数据Service服务实现类，使用EhCache缓存数据
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 12:40 <br/>
 */
@Service(CoreBeanNames.EhCacheTemplateService)
public class EhCacheTemplateService extends BaseService implements ITemplateService {

    @Autowired
    @Qualifier(CoreBeanNames.TemplateDao)
    private TemplateDao templateDao;

    /**
     * 系统配置缓存<br/>
     * <b>注意：不缓存软删除了的数据</b>
     */
    private Cache templateCache = EhCacheUtils.createCache(EhCacheNames.TemplateCache);

    /**
     * 模版名称是否存在
     *
     * @param name 模版名称
     * @return 存在返回true，不存在返回false
     */
    @Override
    public boolean templateNameExists(String name) {
        return templateDao.templateNameExists(name);
    }

    /**
     * 根据模版名称查询模版<br/>
     *
     * @param name 模版名称
     * @return 模版对象, 不存在返回null
     */
    @Override
    public Template getTemplateByName(String name) {
        Template template = null;
        Element element = templateCache.get(name);
        if (element != null && element.getObjectValue() instanceof Template) {
            template = (Template) element.getObjectValue();
        }
        if (template == null) {
            template = templateDao.getByName(name);
            if (template != null) {
                element = new Element(template.getName(), template);
                templateCache.put(element);
            }
        }
        return template;
    }

    /**
     * 保存模版对象，也会保存到缓存中(只缓存状态正常的数据)<br/>
     *
     * @param template 模版对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean saveTemplate(Template template) {
        templateDao.getHibernateDao().save(template);
        if (Template.DEL_FLAG_NORMAL.equals(template.getDelFlag())) {
            Element element = new Element(template.getName(), template);
            templateCache.put(element);
        }
        return true;
    }

    /**
     * 更新模版对象，也会刷新缓存数据(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param template 模版对象
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean updateTemplate(Template template) {
        if (Template.DEL_FLAG_NORMAL.equals(template.getDelFlag())) {
            templateDao.getHibernateDao().update(template, false, true);
            Element element = new Element(template.getName(), template);
            templateCache.put(element);
        } else {
            templateCache.remove(template.getName());
        }
        return true;
    }

    /**
     * 直接删除模版对象，也会从缓存中删除<br/>
     * <b>注意：软删除使用{@link #updateTemplate(Template)}</b>
     *
     * @param name 模版名称
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    @Override
    public boolean deleteTemplate(String name) {
        templateCache.remove(name);
        return templateDao.deleteByName(name);
    }

    /**
     * 获取模版对象(只获取没有被软删除的数据)，直接到数据库里获取，并覆盖到缓存<br/>
     * 1.先根据 name 清除缓存数据<br/>
     * 2.再根据 name 从数据库获取模版对象(只获取没有被软删除的数据)，并覆盖到缓存<br/>
     *
     * @param name 模版名称
     * @return 返回新的Template，否则返回null
     */
    @Override
    public Template refreshTemplate(String name) {
        Template template = templateDao.getByName(name);
        if (template != null && Template.DEL_FLAG_NORMAL.equals(template.getDelFlag())) {
            Element element = new Element(template.getName(), template);
            templateCache.put(element);
        } else {
            templateCache.remove(name);
        }
        return template;
    }

    /**
     * 从数据库查询所有的模版对象，并存到缓存中(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     * 1.先清空缓存<br/>
     * 2.再从数据库查询所有的Template(不包含软删除的数据)，添加到缓存中<br/>
     *
     * @return 所有数据库脚本(不包含软删除的数据)
     */
    @Override
    public List<Template> findAll() {
        templateCache.removeAll();
        List<Template> templateList = templateDao.findAll();
        for (Template template : templateList) {
            if (Template.DEL_FLAG_NORMAL.equals(template.getDelFlag())) {
                Element element = new Element(template.getName(), template);
                templateCache.put(element);
            }
        }
        return templateList;
    }

    /**
     * 获取数据库脚本，使用分页，同时把查询到的数据放入缓存(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param page    分页对象
     * @param name    查询参数：模版名称
     * @param locale  查询参数：模版语言
     * @param id      查询参数：脚本ID
     * @param uuid    查询参数：UUID
     * @param delFlag 查询参数：删除标记
     * @return 分页数据
     */
    @Override
    public Page<Template> findByPage(Page<Template> page, String name, String locale, Long id, String uuid, Character delFlag) {
        page = templateDao.findByPage(page, name, locale, id, uuid, delFlag);
        for (Template template : page.getList()) {
            if (Template.DEL_FLAG_NORMAL.equals(template.getDelFlag())) {
                Element element = new Element(template.getName(), template);
                templateCache.put(element);
            } else {
                templateCache.remove(template.getName());
            }
        }
        return page;
    }
}
