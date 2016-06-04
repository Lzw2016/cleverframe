package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

/**
 * 查询(QLScript)的请求参数对象<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-30 21:46 <br/>
 * TODO 加校验
 */
public class QLScriptQueryVo extends BaseRequestVo {
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
     * 脚本类型，可取："SQL"、"HQL"
     */
    private String scriptType;

    /**
     * 脚本名称，使用包名称+类名+方法名
     */
    private String name;

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

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Character delFlag) {
        this.delFlag = delFlag;
    }
}
