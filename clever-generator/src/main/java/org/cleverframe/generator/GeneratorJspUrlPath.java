package org.cleverframe.generator;

import org.cleverframe.common.IJspUrlPath;

/**
 * 当前Generator模块对应的JSP文件URL路径<br/>
 * <b>注意：此类只保存JSP文件的URL</b><br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-20 15:30 <br/>
 */
public class GeneratorJspUrlPath implements IJspUrlPath {

    /**
     * 代码生成页面主页
     */
    public static final String GeneratorCode = "generator/GeneratorCode";

    /**
     * Generator模块管理页面
     */
    public static final String GeneratorMain = "generator/GeneratorMain";

    /**
     * 代码模版管理页面
     */
    public static final String CodeTemplate = "generator/CodeTemplate";

    /**
     * 根据模版选择数据生成代码
     */
    public static final String TemplateGeneratorCode = "generator/TemplateGeneratorCode";

    /**
     * 根据数据表选择模版生成代码
     */
    public static final String TableGeneratorCode = "generator/TableGeneratorCode";

    /**
     * 代码模版编辑页面
     */
    public static final String TemplateEdit = "generator/TemplateEdit";
}
