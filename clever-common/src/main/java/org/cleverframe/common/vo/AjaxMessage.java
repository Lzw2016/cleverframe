package org.cleverframe.common.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.cleverframe.common.exception.ExceptionUtils;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ajax异步请求的响应消息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-8 21:32 <br/>
 */
@JsonInclude(Include.NON_NULL)
public class AjaxMessage<T> implements Serializable {
    /**
     * 下次请求是否需要验证码
     */
    private boolean isNeedValidateCode = false;

    /**
     * 操作是否成功
     */
    private boolean success = false;

    /**
     * 请求响应返回的数据
     */
    private T result;

    /**
     * 请求成功后跳转地址
     */
    private String successUrl;

    /**
     * 请求失败后跳转地址
     */
    private String failUrl;

    /**
     * 服务端异常的堆栈信息
     */
    private String exceptionStack;

    /**
     * 表单数据验证的错误消息
     */
    private List<ValidMessage> validMessageList;

    /**
     * 默认构造，默认请求操作失败 success=false
     */
    public AjaxMessage() {

    }

    /**
     * @param success 请求结果是否成功
     * @param result  请求响应消息
     * @param e       异常对象
     */
    public AjaxMessage(boolean success, T result, Throwable e) {
        this.success = success;
        this.result = result;
        this.exceptionStack = ExceptionUtils.getStackTraceAsString(e);
    }

    /**
     * @param success 请求结果是否成功
     */
    public AjaxMessage(boolean success) {
        this(success, null, null);
    }

    /**
     * @param success 请求结果是否成功
     * @param result  请求响应消息
     */
    public AjaxMessage(boolean success, T result) {
        this(success, result, null);
    }

    /**
     * 增加验证错误消息<br/>
     *
     * @param validMessage 服务端数据验证错误消息
     * @return 当前对象
     */
    public AjaxMessage addValidMessage(ValidMessage validMessage) {
        if (this.validMessageList == null) {
            this.validMessageList = new ArrayList<>();
        }
        this.validMessageList.add(validMessage);
        return this;
    }

    /**
     * 增加验证错误消息<br/>
     *
     * @param fieldError Spring的验证错误消息
     * @return 当前对象
     */
    public AjaxMessage addValidMessage(FieldError fieldError) {
        ValidMessage validMessage = new ValidMessage(fieldError);
        return addValidMessage(validMessage);
    }

    /**
     * 设置异常信息<br/>
     * 1.请求失败 success=false<br/>
     * 2.给返回的异常堆栈属性赋值(exceptionStack)<br/>
     *
     * @param e 异常对象
     */
    public void setException(Throwable e) {
        this.success = false;
        this.exceptionStack = ExceptionUtils.getStackTraceAsString(e);
    }

    /*--------------------------------------------------------------
     * 			getter、setter
     * -------------------------------------------------------------*/

    public boolean isNeedValidateCode() {
        return isNeedValidateCode;
    }

    public void setNeedValidateCode(boolean needValidateCode) {
        isNeedValidateCode = needValidateCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public String getExceptionStack() {
        return exceptionStack;
    }

    public void setExceptionStack(String exceptionStack) {
        this.exceptionStack = exceptionStack;
    }

    public List<ValidMessage> getValidMessageList() {
        return validMessageList;
    }

    public void setValidMessageList(List<ValidMessage> validMessageList) {
        this.validMessageList = validMessageList;
    }
}
