package org.cleverframe.sys.entity;

import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类，对应表sys_login_log(用户登录日志表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 00:07:05 <br/>
 */
@Entity
@Table(name = "sys_login_log")
@DynamicInsert
@DynamicUpdate
public class LoginLog extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录时间
     */
    private Date loginTime;

    /**
     * 登录的IP地址
     */
    private String loginIp;

    /**
     * 用户代理，客户端信息或浏览器信息
     */
    private String userAgent;

    /**
     * 用户信息，Json格式数据
     */
    private String userInfo;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 登录名
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置 登录名
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取 登录时间
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 设置 登录时间
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 获取 登录的IP地址
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置 登录的IP地址
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取 用户代理，客户端信息或浏览器信息
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置 用户代理，客户端信息或浏览器信息
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取 用户信息，Json格式数据
     */
    public String getUserInfo() {
        return userInfo;
    }

    /**
     * 设置 用户信息，Json格式数据
     */
    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }
}
