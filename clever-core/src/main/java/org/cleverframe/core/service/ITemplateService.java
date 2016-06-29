package org.cleverframe.core.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.core.entity.Template;

import java.util.List;

/**
 * 模版数据Service服务接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-18 12:26 <br/>
 */
public interface ITemplateService {

    /**
     * 模版名称是否存在
     *
     * @param name 模版名称
     * @return 存在返回true，不存在返回false
     */
    boolean templateNameExists(String name);

    /**
     * 根据模版名称查询模版<br/>
     *
     * @param name 模版名称
     * @return 模版对象, 不存在返回null
     */
    Template getTemplateByName(String name);

    /**
     * 保存模版对象，也会保存到缓存中(只缓存状态正常的数据)<br/>
     *
     * @param template 模版对象
     * @return 成功返回true，失败返回false
     */
    boolean saveTemplate(Template template);

    /**
     * 更新模版对象，也会刷新缓存数据(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param template 模版对象
     * @return 成功返回true，失败返回false
     */
    boolean updateTemplate(Template template);

    /**
     * 直接删除模版对象，也会从缓存中删除<br/>
     * <b>注意：软删除使用{@link #updateTemplate(Template)}</b>
     *
     * @param name 模版名称
     * @return 成功返回true，失败返回false
     */
    boolean deleteTemplate(String name);

    /**
     * 获取模版对象(只获取没有被软删除的数据)，直接到数据库里获取，并覆盖到缓存<br/>
     * 1.先根据 name 清除缓存数据<br/>
     * 2.再根据 name 从数据库获取模版对象(只获取没有被软删除的数据)，并覆盖到缓存<br/>
     *
     * @param name 模版名称
     * @return 返回新的Template，否则返回null
     */
    Template refreshTemplate(String name);

    /**
     * 从数据库查询所有的模版对象，并存到缓存中(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     * 1.先清空缓存<br/>
     * 2.再从数据库查询所有的Template(不包含软删除的数据)，添加到缓存中<br/>
     *
     * @return 所有数据库脚本(不包含软删除的数据)
     */
    List<Template> findAll();

    /**
     * 获取数据库脚本，使用分页，同时把查询到的数据放入缓存(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param page    分页对象
     * @param name    查询参数：脚本名称(使用包名称+类名+方法名)
     * @param locale  查询参数：模版语言
     * @param id      查询参数：脚本ID
     * @param uuid    查询参数：UUID
     * @param delFlag 查询参数：删除标记
     * @return 分页数据
     */
    Page<Template> findByPage(Page<Template> page, String name, String locale, Long id, String uuid, Character delFlag);
}
