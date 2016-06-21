package org.cleverframe.generator;

import org.cleverframe.common.IBeanNames;

/**
 * 定义当前Generator模块定义的Spring Bean名称<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 15:31 <br/>
 */
public class GeneratorBeanNames implements IBeanNames {
    // -------------------------------------------------------------------------------------------//
    // Dao
    // -------------------------------------------------------------------------------------------//
    public static final String CodeTemplateDao = "generator_CodeTemplateDao";

    // -------------------------------------------------------------------------------------------//
    // Service
    // -------------------------------------------------------------------------------------------//
    public static final String MateDataService = "generator_MateDataService";
    public static final String CodeFormatService = "generator_CodeFormatService";
    public static final String CodeTemplateService = "generator_CodeTemplateService";

    // -------------------------------------------------------------------------------------------//
    // Other
    // -------------------------------------------------------------------------------------------//
}
