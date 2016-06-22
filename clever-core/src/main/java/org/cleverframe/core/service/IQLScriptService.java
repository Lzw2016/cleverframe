package org.cleverframe.core.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.core.entity.QLScript;

import java.util.List;

/**
 * 数据库脚本Service接口，QLScript数据需要缓存(不要缓存软删除了的数据)<br/>
 * <b>注意：只能使用一种实现，不能混用</b>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-29 10:21 <br/>
 */
public interface IQLScriptService {
    /**
     * 根据脚本名称获取脚本对象，优先到缓存中获取<br/>
     * 1.到QLScript缓存中获取<br/>
     * 2.QLScript缓存中没有才到数据库里获取(只获取未软删除的数据)并存到缓存中<br/>
     * <b>注意：只能返回没有被软删除的QLScript</b>
     *
     * @param name 数据库脚本名称(使用包名称+类名+方法名)
     * @return 数据库脚本信息(QLScript), 不存在返回null
     */
    QLScript getQLScriptByName(String name);

    /**
     * 保存数据库脚本，也会保存到QLScript缓存中(只缓存状态正常的数据)<br/>
     *
     * @param qLScript 数据库脚本对象
     * @return 成功返回true，失败返回false
     */
    boolean saveQLScript(QLScript qLScript);

    /**
     * 更新数据库脚本，也会刷新QLScript缓存数据(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     *
     * @param qLScript 数据库脚本信息(QLScript)
     * @return 成功返回true，失败返回false
     */
    boolean updateQLScript(QLScript qLScript);

    /**
     * 直接删除数据库脚本，也会从QLScript缓存中删除<br/>
     * <b>注意：软删除使用{@link #updateQLScript(QLScript)}</b>
     *
     * @param name 脚本名称(使用包名称+类名+方法名)
     * @return 成功返回true，失败返回false
     */
    boolean deleteQLScript(String name);

    /**
     * 获取数据库脚本(只获取没有被软删除的数据)，直接到数据库里获取，并覆盖到QLScript缓存<br/>
     * 1.先根据 name 清除缓存数据<br/>
     * 2.再根据 name 从数据库获取脚本(只获取没有被软删除的数据)，并覆盖到QLScript缓存<br/>
     *
     * @param name 脚本名称(使用包名称+类名+方法名)
     * @return 返回新的QLScript，否则返回null
     */
    QLScript refreshQLScript(String name);

    /**
     * 从数据库查询所有的QL脚本，并存到QLScript缓存中(只缓存状态正常的数据，如果被软删除就移除缓存数据)<br/>
     * 1.先清空缓存<br/>
     * 2.在从数据库查询所有的QLScript(不包含软删除的数据)，添加到缓存中<br/>
     *
     * @return 所有数据库脚本(不包含软删除的数据)
     */
    List<QLScript> findAllQLScript();

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
    Page<QLScript> findAllQLScript(Page<QLScript> page, String name, String scriptType, Long id, String uuid, Character delFlag);
}
