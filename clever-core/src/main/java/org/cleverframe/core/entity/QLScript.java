package org.cleverframe.core.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据库脚本
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 15:16 <br/>
 */
@Entity
@Table(name = "core_qlscript")
@DynamicInsert
@DynamicUpdate
public class QLScript extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 脚本类型:SQL
     */
    public static final String TYPE_SQL = "SQL";
    /**
     * 脚本类型:HQL
     */
    public static final String TYPE_HQL = "HQL";

    /**
     * 脚本类型，可取："SQL"、"HQL"
     */
    private String scriptType;

    /**
     * 查询脚本，可以使用模版技术拼接
     */
    private String script;

    /**
     * 脚本名称，使用包名称+类名+方法名
     */
    private String name;

    /**
     * 脚本功能说明
     */
    private String description;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getScriptType() {
        return scriptType;
    }

    public void setScriptType(String scriptType) {
        this.scriptType = scriptType;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
