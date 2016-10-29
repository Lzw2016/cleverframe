package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/29 12:53 <br/>
 */
public class RoleQueryVo extends BaseRequestVo {
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
     * 角色名称
     */
    private String name;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
