package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/14 21:14 <br/>
 */
public class LoginSessionQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createDateStart;

    /**
     * 创建时间
     */
    private Date createDateEnd;

    /**
     * 更新时间
     */
    private Date updateDateStart;

    /**
     * 更新时间
     */
    private Date updateDateEnd;

    /**
     * 用户登录名
     */
    private String loginName;

    /**
     * 是否在线（0：否；1：是）
     */
    private Character onLine;

    /**
     * 登录的IP地址
     */
    private String hostIp;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public Date getCreateDateStart() {
        return createDateStart;
    }

    public void setCreateDateStart(Date createDateStart) {
        this.createDateStart = createDateStart;
    }

    public Date getCreateDateEnd() {
        return createDateEnd;
    }

    public void setCreateDateEnd(Date createDateEnd) {
        this.createDateEnd = createDateEnd;
    }

    public Date getUpdateDateStart() {
        return updateDateStart;
    }

    public void setUpdateDateStart(Date updateDateStart) {
        this.updateDateStart = updateDateStart;
    }

    public Date getUpdateDateEnd() {
        return updateDateEnd;
    }

    public void setUpdateDateEnd(Date updateDateEnd) {
        this.updateDateEnd = updateDateEnd;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Character getOnLine() {
        return onLine;
    }

    public void setOnLine(Character onLine) {
        this.onLine = onLine;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }
}
