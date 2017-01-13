package org.cleverframe.doc;

import org.cleverframe.common.IBeanNames;

/**
 * 定义当前Doc模块定义的Spring Bean名称<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2017/1/12 21:42 <br/>
 */
public class DocBeanNames implements IBeanNames {
    // -------------------------------------------------------------------------------------------//
    // Dao
    // -------------------------------------------------------------------------------------------//
    public static final String DocProjectDao = "doc_DocProjectDao";
    public static final String DocDocumentDao = "doc_DocDocumentDao";
    public static final String DocHistoryDao = "doc_DocHistoryDao";

    // -------------------------------------------------------------------------------------------//
    // Service
    // -------------------------------------------------------------------------------------------//
    public static final String DocProjectService = "doc_DocProjectService";
    public static final String DocDocumentService = "doc_DocDocumentService";
    public static final String DocHistoryService = "doc_DocHistoryService";

    // -------------------------------------------------------------------------------------------//
    // Other
    // -------------------------------------------------------------------------------------------//
}
