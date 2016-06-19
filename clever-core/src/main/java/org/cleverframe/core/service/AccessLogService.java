package org.cleverframe.core.service;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.service.BaseService;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.dao.AccessLogDao;
import org.cleverframe.core.entity.AccessLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 22:32 <br/>
 */
@Service(CoreBeanNames.AccessLogService)
public class AccessLogService extends BaseService {

    @Autowired
    @Qualifier(CoreBeanNames.AccessLogDao)
    private AccessLogDao accessLogDao;

    /**
     * 模糊查询访问日志，使用分页
     *
     * @param page             分页对象
     * @param loginName        登录名
     * @param requestStartTime 请求时间(最小)
     * @param requestEndTime   请求时间(最大)
     * @param requestUri       请求URI
     * @param method           操作方式
     * @param params           操作提交的数据
     * @param processMinTime   请求处理时间(最小)
     * @param processMaxTime   请求处理时间(最大)
     * @param remoteAddr       客户端的IP地址
     * @param userAgent        用户代理
     * @param hasException     是否有异常（0：否；1：是）
     * @param exceptionInfo    异常信息
     * @return 分页对象
     */
    public Page<AccessLog> findByPage(Page<AccessLog> page,
                                      String loginName,
                                      Date requestStartTime,
                                      Date requestEndTime,
                                      String requestUri,
                                      String method,
                                      String params,
                                      Long processMinTime,
                                      Long processMaxTime,
                                      String remoteAddr,
                                      String userAgent,
                                      Character hasException,
                                      String exceptionInfo) {
        return accessLogDao.findByPage(page, loginName, requestStartTime, requestEndTime,
                requestUri, method, params,
                processMinTime, processMaxTime,
                remoteAddr, userAgent,
                hasException, exceptionInfo);
    }

    /**
     * 增加访问日志数据
     *
     * @param accessLog 访问日志数据
     * @return 成功返回true，失败返回false
     */
    @Transactional(readOnly = false)
    public boolean addAccessLog(AccessLog accessLog) {
        accessLogDao.getHibernateDao().save(accessLog);
        return true;
    }
}
