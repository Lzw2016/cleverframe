package org.cleverframe.core;

import org.cleverframe.common.IBeanNames;
import org.cleverframe.common.spring.SpringBeanNames;

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

    public static final String TemplateDao = "core_TemplateDao";


    // -------------------------------------------------------------------------------------------//
    // Service
    // -------------------------------------------------------------------------------------------//
    public static final String EhCacheQLScriptService = "core_EhCacheQLScriptService";

    // -------------------------------------------------------------------------------------------//
    // Other
    // -------------------------------------------------------------------------------------------//
    public static final String QLScriptTemplateLoader = "core_QLScriptTemplateLoader";

    public static final String UserUtilsByTemp = "core_UserUtilsByTemp";

}
