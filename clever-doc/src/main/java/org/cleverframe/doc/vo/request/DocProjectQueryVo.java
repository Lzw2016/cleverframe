package org.cleverframe.doc.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2017/1/15 0:23 <br/>
 */
public class DocProjectQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 文档名称
     */
    private String name;

    /**
     * 创建人
     */
    private String createBy;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
}
