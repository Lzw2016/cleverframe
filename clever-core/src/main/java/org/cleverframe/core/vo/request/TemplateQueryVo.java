package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-17 15:55 <br/>
 */
public class TemplateQueryVo extends BaseRequestVo {
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
     * 模版名称
     */
    private String name;

    /**
     * 模版语言
     */
    private String locale;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
