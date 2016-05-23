package org.cleverframe.common.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.time.DateTimeUtils;
import org.cleverframe.common.vo.AjaxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * SpringMVC实现的控制器基类<br/>
 * 1.设置请求参数绑定配置，包括防止XSS攻击<br/>
 * 2.提供请求参数验证功能<br/>
 * TODO 3.提供IUserUtils，方便获取当前用户的组织架构信息<br/>
 *
 * @author LiZW
 * @version 2015年5月28日 下午4:38:30
 */
public abstract class BaseController {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 验证Bean实例对象
     */
    @Autowired
    @Qualifier(SpringBeanNames.Validator)
    protected Validator validator;

    /**
     * 初始化数据绑定 <br/>
     * 1.将所有传递进来的String进行HTML编码，防止XSS攻击 <br/>
     * 2.将字段中Date类型转换为String类型<br/>
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                // 可以设置不过滤的url路径，对于某些请求不进行HTML编码
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });

        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateTimeUtils.parseDate(text));
            }
        });
    }

    /**
     * 将Spring验证信息放入AjaxMessage对象中<br/>
     * BindingResult中有错误AjaxMessage的Success属性就会被设置成False<br/>
     *
     * @param bindingResult Spring验证的错误消息
     * @param message       AjaxMessage对象
     * @return 验证有错误返回False，无错误返回True
     */
    protected boolean beanValidator(BindingResult bindingResult, AjaxMessage<?> message) {
        if (bindingResult.hasErrors()) {
            message.setSuccess(false);
            List<FieldError> validError = bindingResult.getFieldErrors();
            for (FieldError fieldError : validError) {
                String entityName = fieldError.getObjectName();
                String filed = fieldError.getField();
                String errorMessage = fieldError.getDefaultMessage();
                String code = fieldError.getCode();
                String value = fieldError.getRejectedValue() == null ? "null" : fieldError.getRejectedValue().toString();
                message.addValidMessage(fieldError);
                logger.debug("###数据验证失败 entityName:" + entityName
                        + " | filed:" + filed
                        + " | errorMessage:" + errorMessage
                        + " | code:" + code
                        + " | value:" + value);
            }
        } else {
            message.setSuccess(true);
        }
        return !bindingResult.hasErrors();
    }
}
