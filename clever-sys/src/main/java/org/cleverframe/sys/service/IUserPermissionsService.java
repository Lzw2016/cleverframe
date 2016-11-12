package org.cleverframe.sys.service;

import org.cleverframe.sys.entity.Resources;

import java.io.Serializable;

/**
 * 用户权限管理服务,为UserPermissionsAuthorizationFilter提供服务<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/12 10:47 <br/>
 */
public interface IUserPermissionsService {

    /**
     * 根据带有变量的Url获取实际的Url地址
     * @param resourcesUrl 数据库保存的含有变量的Url
     * @return 实际的Url地址
     */
    String getResourcesKey(String resourcesUrl);

    /**
     * 重新加载(缓存)全部资源，当资源更新后调用此方法
     *
     * @return 成功返回true
     */
    boolean reloadResources();

    /**
     * 根据ResourcesKey从缓存中获取Resources
     * @return 不存在返回null
     */
    Resources getResources(String resourcesKey);

    /**
     * 保存资源信息,顺便保存到缓存中
     *
     * @return 成功返回true
     */
    boolean addResources(Resources resources);

    /**
     * 保存资源信息,顺便更新缓存中的数据
     *
     * @return 成功返回true
     */
    boolean updateResources(Resources resources);

    /**
     * 删除资源信息,顺便删除缓存中的数据
     *
     * @return 成功返回true
     */
    boolean deleteResources(Serializable resourcesId);
}
