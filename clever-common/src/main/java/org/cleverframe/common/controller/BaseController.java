package org.cleverframe.common.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.cleverframe.common.attributes.CommonRequestAttributes;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.time.DateTimeUtils;
import org.cleverframe.common.user.IUserUtils;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * SpringMVC实现的控制器基类<br/>
 * 1.设置请求参数绑定配置，包括防止XSS攻击<br/>
 * 2.提供请求参数验证功能<br/>
 * 3.统一处理Controller层的异常<br/>
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
     * Json视图、XML视图使用的根节点键值，需要与配置里的值一致<br/>
     */
    public final static String XML_OR_JSON_ROOT = "xmlOrJsonRoot";

    /**
     * 视图页面(JSP)的后缀
     */
    protected final static String VIEW_PAGE_SUFFIX = ".html";

    /**
     * 用户信息获取接口
     */
    protected static final IUserUtils userUtils;

    static {
        userUtils = SpringContextHolder.getBean(SpringBeanNames.UserUtils);
        if (userUtils == null) {
            RuntimeException exception = new RuntimeException("### IUserUtils注入失败,BeanName=[" + SpringBeanNames.UserUtils + "]");
            logger.error(exception.getMessage(), exception);
        } else {
            logger.debug("### IUserUtils注入成功,BeanName=[" + SpringBeanNames.UserUtils + "]");
        }
    }

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

    /**
     * 默认的异常处理方法<br/>
     * 1.返回AjaxMessage<br/>
     * 2.错误信息存到request Attribute中，给拦截器处理(存储)<br/>
     */
    @ExceptionHandler(value = Throwable.class)
    protected ModelAndView defaultErrorHandler(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
        ModelAndView modelAndView = new ModelAndView();

        // 错误信息存到request Attribute中，给拦截器处理(存储)
        request.setAttribute(CommonRequestAttributes.SERVER_EXCEPTION, throwable);

        // 如果是ajax请求直接返回响应数据，跳转到对应的错误页面
        if ("true".equals(request.getParameter("ajaxRequest")) || request.getRequestURI().endsWith(".json") || request.getRequestURI().endsWith(".xml")) {
            modelAndView.getModelMap().put(XML_OR_JSON_ROOT, new AjaxMessage<>(throwable, "服务器异常!"));
        }
        return modelAndView;
    }
}
