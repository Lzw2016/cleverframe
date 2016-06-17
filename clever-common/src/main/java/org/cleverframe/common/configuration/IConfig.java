package org.cleverframe.common.configuration;

import java.util.Map;

/**
 * 配置信息获取或修改接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-11 23:42 <br/>
 */
public interface IConfig {
    /**
     * 获取配置信息,没有配置可以使用默认值
     *
     * @param key 配置键
     * @param defaultValue 默认值
     * @return 配置值，不存在返回defaultValue
     */
    String getConfig(String key, String defaultValue);

    /**
     * 获取配置信息
     *
     * @param key 配置键
     * @return 配置值，不存在返回null
     */
    String getConfig(String key);

    /**
     * 获取所有的配置信息
     *
     * @return 所有的配置
     */
    Map<String, String> getAllConfig();

    /**
     * 重新加载某个配置
     *
     * @param key 配置键
     * @return 加载成功返回true，失败返回false
     */
    boolean reLoad(String key);

    /**
     * 重新加载所有的配置
     *
     * @return 加载成功返回true，失败返回false
     */
    boolean reLoadAll();

    /**
     * 更新某个配置<br/>
     * 调用前需要检查配置是否支持更新，调用isCanUpdate(String key)<br/>
     *
     * @param key   配置键
     * @param value 配置值
     * @return 更新成功返回true，失败返回false
     * @see #isCanUpdate(String)
     */
    boolean updateConfig(String key, String value);

    /**
     * 新增或者更新配置信息(存在就更新，不存在就新增)<br/>
     * <b>
     *     注意：新增时 isCanUpdate = false<br/>
     * </b>
     * @param key 配置键
     * @param value 配置值
     * @return 新增返回true,更新返回false
     */
    boolean updateOrAddConfig(String key, String value);

    /**
     * 检查配置是否支持更新操作<br/>
     * 此处的更新是指能够在线更新生效，不需要重启服务器<br/>
     *
     * @param key 配置键
     * @return 支持返回true，不支持返回false
     */
    boolean isCanUpdate(String key);

    /**
     * 清除所有缓存数据
     */
    void clearAllCache();
}
