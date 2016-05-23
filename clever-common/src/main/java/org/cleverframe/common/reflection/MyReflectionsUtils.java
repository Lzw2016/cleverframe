package org.cleverframe.common.reflection;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类，使用org.reflections实现<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-22 21:52 <br/>
 */
public class MyReflectionsUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(MyReflectionsUtils.class);

    /**
     * 反射操作类
     */
    private static final Reflections REFLECTIONS;

    static {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setUrls(ClasspathHelper.forPackage("org.cleverframe"));
        configurationBuilder.setScanners(new SubTypesScanner(false), new TypeAnnotationsScanner());

        REFLECTIONS = new Reflections(configurationBuilder);
    }

    public static void test() {
        // 获取所有的类名
//        Set<String> set = REFLECTIONS.getAllTypes();
//        for (String str : set) {
//            logger.info(str);
//        }

        // 获取所有的类型
//        Set<Class<? extends Object>> allClasses = REFLECTIONS.getSubTypesOf(Object.class);
//        for (Class<? extends Object> clzz : allClasses) {
//            logger.info(clzz.getName());
//        }





    }


}
