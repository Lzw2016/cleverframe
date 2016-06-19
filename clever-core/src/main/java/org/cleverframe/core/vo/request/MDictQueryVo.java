package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 10:42 <br/>
 */
public class MDictQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据全局标识UUID
     */
    private String uuid;

    /**
     * 数据ID
     */
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    private Character delFlag;

    /**
     * 字典键
     */
    private String mdictKey;

    /**
     * 字典分类
     */
    private String mdictType;

    /*--------------------------------------------------------------
    *          getter、setter
    * -------------------------------------------------------------*/

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Character getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Character delFlag) {
        this.delFlag = delFlag;
    }

    public String getMdictKey() {
        return mdictKey;
    }

    public void setMdictKey(String mdictKey) {
        this.mdictKey = mdictKey;
    }

    public String getMdictType() {
        return mdictType;
    }

    public void setMdictType(String mdictType) {
        this.mdictType = mdictType;
    }
}
