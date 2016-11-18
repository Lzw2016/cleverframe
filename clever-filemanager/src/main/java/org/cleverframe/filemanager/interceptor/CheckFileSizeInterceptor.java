package org.cleverframe.filemanager.interceptor;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.cleverframe.common.mapper.JacksonMapper;
import org.cleverframe.common.vo.response.AjaxMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 此拦截器只拦截文件上传路径，并只检查上传文件的大小 -- (此方法无法解决！)<br/>
 * 当上传超过限制大小的文件时 浏览器接收不到服务器返回的异常提示信息，而提示 net::err_connection_reset 错误，解决方案如下：<br/>
 * https://my.oschina.net/ironwill/blog/646762<br/>
 * http://gold.xitu.io/entry/57b57db92e958a00562eb08f<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/18 22:22 <br/>
 */
public class CheckFileSizeInterceptor implements HandlerInterceptor {
    /**
     * 日志对象
     */
    private final static Logger logger = LoggerFactory.getLogger(CheckFileSizeInterceptor.class);

    /**
     * 上传文件大小限制
     */
    private long maxUploadSize;

    public CheckFileSizeInterceptor(Long maxUploadSize) {
        this.maxUploadSize = maxUploadSize;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否是多文件上传的请求
        if (ServletFileUpload.isMultipartContent(request)) {
            ServletRequestContext requestContext = new ServletRequestContext(request);
            long requsetSize = requestContext.contentLength();
            if (requsetSize > maxUploadSize) {
                // throw new MaxUploadSizeExceededException(maxUploadSize);
                logger.warn("上传文件超过限制大小，限制大小{}，当前上传文件大小{}", maxUploadSize, requsetSize);
                AjaxMessage message = new AjaxMessage(false, null, "文件上传超过最大限制");
                response.getWriter().print(JacksonMapper.nonEmptyMapper().toJson(message));
                response.getWriter().flush();
                return false;
            }
            logger.info("上传文件小于限制大小，限制大小{}，当前上传文件大小{}", maxUploadSize, requsetSize);
        } else {
            logger.debug("该请求没有上传文件");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // logger.debug("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // logger.debug("afterCompletion");
    }
}
