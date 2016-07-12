package org.cleverframe.core.interceptor;

import org.cleverframe.common.attributes.CommonApplicationAttributes;
import org.cleverframe.common.attributes.CommonRequestAttributes;
import org.cleverframe.common.interceptor.IRequestStatistics;
import org.cleverframe.common.mapper.BeanMapper;
import org.cleverframe.common.time.DateTimeUtils;
import org.cleverframe.common.utils.ConversionUtils;
import org.cleverframe.common.vo.request.RequestInfo;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.AccessLog;
import org.cleverframe.core.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 服务所有的请求统计默认实现类，不依赖任何中间件<br/>
 * 使用ApplicationAttributes存储访问统计数据，使用数据库存储请求信息<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-19 17:09 <br/>
 */
// TODO RequestStatisticsImpl 未实现
public class RequestStatisticsImpl implements IRequestStatistics {

    @Autowired
    @Qualifier(CoreBeanNames.AccessLogService)
    private AccessLogService accessLogService;

    /**
     * 服务器本次启动后处理的请求总数,类型:long
     */
    private static volatile long REQUEST_COUNT_BY_START;

    /**
     * 服务器当天处理请求总数(00:00:00--23:59:59),类型:long
     */
    private static volatile long REQUEST_COUNT_BY_DAY;

    /**
     * 统计服务器当前小时处理请求总数(n:00:00-n:59:59),类型:long
     */
    private static volatile long REQUEST_COUNT_BY_HOUR;

    /**
     * 最后一次请求的时间,类型:long
     */
    private static volatile long LAST_REQUEST_TIME = System.currentTimeMillis();

    /**
     * 服务器本次启动后处理的请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByStart(HttpServletRequest request, HttpServletResponse response) {
        REQUEST_COUNT_BY_START++;
        request.getServletContext().setAttribute(CommonApplicationAttributes.REQUEST_COUNT_BY_START, REQUEST_COUNT_BY_START);
        return true;
    }

    /**
     * 服务器当天处理请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByDay(HttpServletRequest request, HttpServletResponse response) {
        Date endDate = DateTimeUtils.getDayEndTime(new Date(LAST_REQUEST_TIME));
        if (endDate.compareTo(new Date()) < 0) {
            REQUEST_COUNT_BY_DAY = 1;
        } else {
            REQUEST_COUNT_BY_DAY++;
        }
        request.getServletContext().setAttribute(CommonApplicationAttributes.REQUEST_COUNT_BY_DAY, REQUEST_COUNT_BY_DAY);
        return true;
    }

    /**
     * 统计服务器当前小时处理请求总数 加1
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean addRequestCountByHour(HttpServletRequest request, HttpServletResponse response) {
        Date endDate = DateTimeUtils.getHourEndTime(new Date(LAST_REQUEST_TIME));
        if (endDate.compareTo(new Date()) < 0) {
            REQUEST_COUNT_BY_HOUR = 1;
        } else {
            REQUEST_COUNT_BY_HOUR++;
        }
        request.getServletContext().setAttribute(CommonApplicationAttributes.REQUEST_COUNT_BY_HOUR, REQUEST_COUNT_BY_HOUR);
        return true;
    }

    /**
     * @return 服务器本次启动后处理的请求总数
     */
    @Override
    public long getRequestCountByStart(HttpServletRequest request, HttpServletResponse response) {
        return REQUEST_COUNT_BY_START;
    }

    /**
     * @return 服务器当天处理请求总数
     */
    @Override
    public long getRequestCountByDay(HttpServletRequest request, HttpServletResponse response) {
        return REQUEST_COUNT_BY_DAY;
    }

    /**
     * @return 统计服务器当前小时处理请求总数
     */
    @Override
    public long getRequestCountByHour(HttpServletRequest request, HttpServletResponse response) {
        return REQUEST_COUNT_BY_HOUR;
    }

    /**
     * 设置最后一次请求的时间
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean setLastRequestTime(HttpServletRequest request, HttpServletResponse response) {
        LAST_REQUEST_TIME = System.currentTimeMillis();
        request.getServletContext().setAttribute(CommonApplicationAttributes.LAST_REQUEST_TIME, LAST_REQUEST_TIME);
        return true;
    }

    /**
     * @return 返回最后一次请求的时间
     */
    @Override
    public long getLastRequestTime(HttpServletRequest request, HttpServletResponse response) {
        return LAST_REQUEST_TIME;
    }

    /**
     * 设置当前请求请求时间
     *
     * @return 操作成功返回 true
     */
    @Override
    public boolean setRequestStartTime(HttpServletRequest request, HttpServletResponse response) {
        long lastRequestTime = System.currentTimeMillis();
        request.setAttribute(CommonRequestAttributes.REQUEST_START_TIME, lastRequestTime);
        return true;
    }

    /**
     * @return 返回当前请求请求时间
     */
    @Override
    public long getRequestStartTime(HttpServletRequest request, HttpServletResponse response) {
        return ConversionUtils.converter(request.getAttribute(CommonRequestAttributes.REQUEST_START_TIME), System.currentTimeMillis());
    }

    /**
     * 存储请求信息
     *
     * @param requestInfo 请求信息
     * @return 操作成功返回 true
     */
    @Override
    public boolean saveRequestInfo(HttpServletRequest request, HttpServletResponse response, RequestInfo requestInfo) {
        AccessLog accessLog = BeanMapper.mapper(requestInfo, AccessLog.class);
        accessLogService.addAccessLog(accessLog);
        return true;
    }
}
