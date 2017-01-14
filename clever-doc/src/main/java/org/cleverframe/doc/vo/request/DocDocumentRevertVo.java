package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/14 16:59 <br/>
 */
public class DocDocumentRevertVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 文档ID
     */
    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    /**
     * 文档历史ID
     */
    @NotNull(message = "文档历史ID不能为空")
    private Long historyId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }
}
