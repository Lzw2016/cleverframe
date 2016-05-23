//package org.cleverframe.common.config;
//
//import java.util.Map;
//import java.util.Map.Entry;
//import java.util.Set;
//
//import com.google.common.collect.Maps;
//
///**
// * 管理系统全局配置
// *
// * @author LiZW
// * @version 2015年5月25日 下午1:43:33
// */
//public class GlobalConfig {
//    /**
//     * 保存全局属性值
//     */
//    private static Map<String, String> map = Maps.newHashMap();
//
//    /**
//     * 属性文件加载对象
//     */
//    private static PropertiesLoader propertiesLoader = new PropertiesLoader("cleverframe.properties");
//
//    /**
//     * 获取配置
//     */
//    public static String getConfig(String key) {
//        String value = map.get(key);
//        if (value == null) {
//            value = propertiesLoader.getProperty(key);
//            map.put(key, value);
//        }
//        return value;
//    }
//
//    /**
//     * 获取所有的属性配置
//     */
//    public static Map<String, String> getAllConfig() {
//        Map<String, String> configAll = Maps.newHashMap();
//        Set<Entry<Object, Object>> temMap = propertiesLoader.getProperties().entrySet();
//        for (Entry<Object, Object> entry : temMap) {
//            configAll.put(entry.getKey().toString(), entry.getValue().toString());
//        }
//        return configAll;
//    }
//
//    // -------------------------------------------------------------------------------------------//
//    // EL 自定义函数，获取全局配置信息
//    // -------------------------------------------------------------------------------------------//
//
//    /**
//     * 静态资源基路径
//     */
//    public static String getStaticPath() {
//        return GlobalConfig.getConfig(GlobalPropertiesNames.STATIC_PATH);
//    }
//
//    /**
//     * 系统HTML、Jsp、Freemarker等系统页面基路径
//     */
//    public static String getViewsPath() {
//        return GlobalConfig.getConfig(GlobalPropertiesNames.VIEWS_PATH);
//    }
//
//    /**
//     * 系统文档基路径
//     */
//    public static String getDocPath() {
//        return GlobalConfig.getConfig(GlobalPropertiesNames.DOC_PATH);
//    }
//
//    /**
//     * MVC框架的请求映射基路径
//     */
//    public static String getMvcPath() {
//        return GlobalConfig.getConfig(GlobalPropertiesNames.MVC_PATH);
//    }
//}
