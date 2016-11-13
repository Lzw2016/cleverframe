package org.cleverframe.sys.entity;

import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * 存储Shiro框架Session数据(此表数据只是用户登入Session数据，必要时可清空此表)
 * 作者：LiZW <br/>
 * 创建时间：2016/11/13 22:40 <br/>
 */
@Entity
@Table(name = "sys_login_session")
@DynamicInsert
@DynamicUpdate
public class LoginSession extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 资源标题
     */
    private String sessionId;

    /**
     * 用户登录名
     */
    private String loginName;

    /**
     * Shiro框架Session对象序列化数据
     */
    @Lob
    @Column(columnDefinition = "MediumBlob")
    private byte[] sessionObject;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取 更新时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置 更新时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 获取 资源标题
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * 设置 资源标题
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 获取 用户登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置 用户登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取 Shiro框架Session对象序列化数据
     */
    public byte[] getSessionObject() {
        return sessionObject;
    }

    /**
     * 设置 Shiro框架Session对象序列化数据
     */
    public void setSessionObject(byte[] sessionObject) {
        this.sessionObject = sessionObject;
    }
}
