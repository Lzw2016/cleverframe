package org.cleverframe.core.interceptor;

import org.cleverframe.common.interceptor.IRequestStatistics;
import org.cleverframe.common.vo.request.RequestInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务所有的请求统计默认实现类，不依赖任何中间件<br/>
 * 使用ApplicationAttributes存储访问统计数据，使用数据库存储请求信息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 17:09 <br/>
 */
// TODO IRequestStatistics 未实现
public class RequestStatisticsImpl implements IRequestStatistics {
    /**
     * 服务器本次启动后处理的请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByStart(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    /**
     * 服务器当天处理请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByDay(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    /**
     * 统计服务器当前小时处理请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByHour(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    /**
     * @return 服务器本次启动后处理的请求总数
     */
    @Override
    public long getRequestCountByStart(HttpServletRequest request, HttpServletResponse response) {
        return 0;
    }

    /**
     * @return 服务器当天处理请求总数
     */
    @Override
    public long getRequestCountByDay(HttpServletRequest request, HttpServletResponse response) {
        return 0;
    }

    /**
     * @return 统计服务器当前小时处理请求总数
     */
    @Override
    public long getRequestCountByHour(HttpServletRequest request, HttpServletResponse response) {
        return 0;
    }

    /**
     * 设置最后一次请求的时间
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean setLastRequestTime(HttpServletRequest request, HttpServletResponse response) {
        return false;
    }

    /**
     * @return 返回最后一次请求的时间
     */
    @Override
    public long getLastRequestTime(HttpServletRequest request, HttpServletResponse response) {
        return 0;
    }

    /**
     * 存储请求信息
     *
     * @param requestInfo 请求信息
     * @return 操作成功返回 true
     */
    @Override
    public boolean saveRequestInfo(HttpServletRequest request, HttpServletResponse response, RequestInfo requestInfo) {
        return false;
    }
}
