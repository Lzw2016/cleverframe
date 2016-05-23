package org.cleverframe.common.exception;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 0:19 <br/>
 */
public class ExceptionUtils {
    /**
     * 将CheckedException转换为UncheckedException.<br/>
     *
     * @param e 需要try...catch...的异常
     * @return 不需要try...catch...的异常
     */
    public static RuntimeException unchecked(Throwable e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }

    /**
     * 将ErrorStack转化为String(获取异常的堆栈信息)<br/>
     *
     * @param e 异常对象
     * @return 异常的堆栈信息
     */
    public static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 判断异常是否由某些底层的异常引起.<br/>
     *
     * @param ex                    异常对象
     * @param causeExceptionClasses 异常类型数组
     * @return 如果异常对象(ex)的内部异常含有异常类型数组(causeExceptionClasses)中的异常类型返回true，否则返回false
     */
    @SuppressWarnings("unchecked")
    public static boolean isCausedBy(Throwable ex, Class<? extends Throwable>... causeExceptionClasses) {
        Throwable cause = ex.getCause();
        while (cause != null) {
            for (Class<? extends Throwable> causeClass : causeExceptionClasses) {
                if (causeClass.isInstance(cause)) {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }

    /**
     * 在request中获取异常类<br/>
     *
     * @param request HTTP请求对象
     * @return request中获取异常对象
     */
    public static Throwable getThrowable(HttpServletRequest request) {
        Throwable ex = null;
        if (request.getAttribute("exception") != null) {
            ex = (Throwable) request.getAttribute("exception");
        } else if (request.getAttribute("javax.servlet.error.exception") != null) {
            ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
        }
        return ex;
    }
}
