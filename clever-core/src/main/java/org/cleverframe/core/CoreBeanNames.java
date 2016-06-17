package org.cleverframe.core;

import org.cleverframe.common.IBeanNames;

/**
 * 定义当前core模块定义的Spring Bean名称<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-12 9:49 <br/>
 */
public class CoreBeanNames implements IBeanNames {
    // -------------------------------------------------------------------------------------------//
    // Dao
    // -------------------------------------------------------------------------------------------//
    public static final String QLScriptDao = "core_QLScriptDao";

    public static final String ConfigDao = "core_ConfigDao";


    // -------------------------------------------------------------------------------------------//
    // Service
    // -------------------------------------------------------------------------------------------//
    public static final String EhCacheQLScriptService = "core_EhCacheQLScriptService";

    public static final String EhCacheConfigService = "core_EhCacheConfigService";


    // -------------------------------------------------------------------------------------------//
    // Other
    // -------------------------------------------------------------------------------------------//
    public static final String QLScriptTemplateLoader = "core_QLScriptTemplateLoader";

    public static final String UserUtilsByTemp = "core_UserUtilsByTemp";

}
