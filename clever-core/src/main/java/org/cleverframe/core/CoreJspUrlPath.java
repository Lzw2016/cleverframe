package org.cleverframe.core;

import org.cleverframe.common.IJspUrlPath;

/**
 * 当前core模块对应的JSP文件URL路径<br/>
 * <b>注意：此类只保存JSP文件的URL</b><br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-14 0:11 <br/>
 */
public class CoreJspUrlPath implements IJspUrlPath {
    /**
     * core模块主页
     */
    public static final String CoreMain = "core/CoreMain";

    /**
     * 数据库脚本管理页
     */
    public static final String QLScript = "core/QLScript";

    /**
     * 系统配置管理页
     */
    public static final String Config = "core/Config";

    /**
     * 系统FreeMarker模版管理
     */
    public static final String Template = "core/Template";

    /**
     * 数据字典管理
     */
    public static final String Dict = "core/Dict";


}
