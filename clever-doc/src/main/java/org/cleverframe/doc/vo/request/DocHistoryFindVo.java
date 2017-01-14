package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import javax.validation.constraints.NotNull;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/14 16:48 <br/>
 */
public class DocHistoryFindVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "文档ID不能为空")
    private Long documentId;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }
}
