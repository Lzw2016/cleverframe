package org.cleverframe.common.vo.request;

import java.io.Serializable;
import java.util.Date;

/**
 * 记录请求信息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:32 <br/>
 */
public class RequestInfo implements Serializable {
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式,POST、GET...
     */
    private String method;

    /**
     * 请求参数数据
     */
    private String params;

    /**
     * 请求处理时间
     */
    private Long processTime;

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

    /**
     * 异常信息
     */
    private String exceptionInfo;

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
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

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
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

    public String getExceptionInfo() {
        return exceptionInfo;
    }

    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }

    @Override
    public String toString() {
        return "RequestInfo{" +
                "loginName='" + loginName + '\'' +
                ", requestTime=" + requestTime +
                ", requestUri='" + requestUri + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", processTime=" + processTime +
                ", remoteAddr='" + remoteAddr + '\'' +
                ", userAgent='" + userAgent + '\'' +
                ", hasException='" + hasException + '\'' +
                ", exceptionInfo='" + (exceptionInfo == null ? "0" : exceptionInfo.length()) + '\'' +
                '}';
    }
}
