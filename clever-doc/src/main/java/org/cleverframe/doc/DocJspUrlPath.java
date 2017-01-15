package org.cleverframe.doc;

import org.cleverframe.common.IJspUrlPath;

/**
 * 当前Generator模块对应的JSP文件URL路径<br/>
 * <b>注意：此类只保存JSP文件的URL</b><br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2017/1/12 21:44 <br/>
 */
public class DocJspUrlPath implements IJspUrlPath {

    /**
     * 文档编辑器页面
     */
    public static final String DocumentEdit = "doc/DocumentEdit";

    /**
     * 文档阅读页面
     */
    public static final String DocumentRead = "doc/DocumentRead";

    /**
     * 文档项目管理
     */
    public static final String DocProjectManager = "doc/DocProjectManager";

    /**
     * 文档项目编辑
     */
    public static final String DocProjectEdit = "doc/DocProjectEdit";

    /**
     * 文档项目阅读
     */
    public static final String DocProjectRead = "doc/DocProjectRead";
}
