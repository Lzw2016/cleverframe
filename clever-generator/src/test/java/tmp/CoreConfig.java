package tmp;

import org.cleverframe.core.persistence.entity.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类，对应表core_config(配置表)<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-21 21:19 <br/>
 */
@Entity
@Table(name = "core_config")
@DynamicInsert
@DynamicUpdate
public class CoreConfig extends IdEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
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
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 请求URI
     */
    public String getRequestUri() {
        return requestUri;
    }

    /**
     * 设置 请求URI
     */
    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    /**
     * 获取 操作方式
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置 操作方式
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * 获取 操作提交的数据
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置 操作提交的数据
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * 获取 请求处理时间
     */
    public Long getProcessTime() {
        return processTime;
    }

    /**
     * 设置 请求处理时间
     */
    public void setProcessTime(Long processTime) {
        this.processTime = processTime;
    }

    /**
     * 获取 客户端的IP地址
     */
    public String getRemoteAddr() {
        return remoteAddr;
    }

    /**
     * 设置 客户端的IP地址
     */
    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    /**
     * 获取 用户代理
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * 设置 用户代理
     */
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * 获取 是否有异常（0：否；1：是）
     */
    public Character getHasException() {
        return hasException;
    }

    /**
     * 设置 是否有异常（0：否；1：是）
     */
    public void setHasException(Character hasException) {
        this.hasException = hasException;
    }

    /**
     * 获取 异常信息
     */
    public String getExceptionInfo() {
        return exceptionInfo;
    }

    /**
     * 设置 异常信息
     */
    public void setExceptionInfo(String exceptionInfo) {
        this.exceptionInfo = exceptionInfo;
    }
}