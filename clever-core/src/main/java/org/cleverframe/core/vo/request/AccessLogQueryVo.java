package org.cleverframe.core.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 22:58 <br/>
 */
public class AccessLogQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 请求时间(最小)
     */
    private Date requestStartTime;

    /**
     * 请求时间(最大)
     */
    private Date requestEndTime;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式,POST、GET...
     */
    private String method;

    /**
     * 请求处理时间(最小)
     */
    private Long processMinTime;

    /**
     * 请求处理时间(最大)
     */
    private Long processMaxTime;

    /**
     * 客户端的IP地址
     */
    private String remoteAddr;
    /**
     * 用户代理
     */
    private String userAgent;
    /**
     * 是否有异常（0：否；1：是）
     */
    private Character hasException;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getRequestStartTime() {
        return requestStartTime;
    }

    public void setRequestStartTime(Date requestStartTime) {
        this.requestStartTime = requestStartTime;
    }

    public Date getRequestEndTime() {
        return requestEndTime;
    }

    public void setRequestEndTime(Date requestEndTime) {
        this.requestEndTime = requestEndTime;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getProcessMinTime() {
        return processMinTime;
    }

    public void setProcessMinTime(Long processMinTime) {
        this.processMinTime = processMinTime;
    }

    public Long getProcessMaxTime() {
        return processMaxTime;
    }

    public void setProcessMaxTime(Long processMaxTime) {
        this.processMaxTime = processMaxTime;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public Character getHasException() {
        return hasException;
    }

    public void setHasException(Character hasException) {
        this.hasException = hasException;
    }
}
