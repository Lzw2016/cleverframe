package org.cleverframe.common.interceptor;

import org.cleverframe.common.vo.request.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务所有的请求统计，统计以下信息：<br/>
 * 1.服务器本次启动后处理的请求总数<br/>
 * 2.服务器当天处理请求总数(00:00:00--23:59:59)<br/>
 * 3.统计服务器当前小时处理请求总数(n:00:00-n:59:59)<br/>
 * 4.存储个请求信息<br/>
 * 5.<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-16 14:47 <br/>
 */
public interface IRequestStatistics {

    /**
     * 服务器本次启动后处理的请求总数 加1
     *
     * @return 操作成功返回 true
     */
    boolean addRequestCountByStart(HttpServletRequest request, HttpServletResponse response);

    /**
     * 服务器当天处理请求总数(00:00:00--23:59:59) 加1
     *
     * @return 操作成功返回 true
     */
    boolean addRequestCountByDay(HttpServletRequest request, HttpServletResponse response);

    /**
     * 服务器当前小时处理请求总数(n:00:00-n:59:59) 加1
     *
     * @return 操作成功返回 true
     */
    boolean addRequestCountByHour(HttpServletRequest request, HttpServletResponse response);

    /**
     * @return 服务器本次启动后处理的请求总数
     */
    long getRequestCountByStart(HttpServletRequest request, HttpServletResponse response);

    /**
     * @return 服务器当天处理请求总数
     */
    long getRequestCountByDay(HttpServletRequest request, HttpServletResponse response);

    /**
     * @return 服务器当前小时处理请求总数
     */
    long getRequestCountByHour(HttpServletRequest request, HttpServletResponse response);

    /**
     * 设置最后一次请求的时间
     *
     * @return 操作成功返回 true
     */
    boolean setLastRequestTime(HttpServletRequest request, HttpServletResponse response);

    /**
     * @return 返回最后一次请求的时间
     */
    long getLastRequestTime(HttpServletRequest request, HttpServletResponse response);

    /**
     * 存储请求信息
     *
     * @param requestInfo 请求信息
     * @return 操作成功返回 true
     */
    boolean saveRequestInfo(HttpServletRequest request, HttpServletResponse response, RequestInfo requestInfo);
}
