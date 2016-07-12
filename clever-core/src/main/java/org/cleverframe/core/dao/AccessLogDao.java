package org.cleverframe.core.dao;

import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.CoreBeanNames;
import org.cleverframe.core.entity.AccessLog;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-6-19 22:30 <br/>
 */
@Repository(CoreBeanNames.AccessLogDao)
public class AccessLogDao extends BaseDao<AccessLog> {

//    login_name      varchar(20)                                         COMMENT '登录名',
//    request_time    datetime        NOT NULL                            COMMENT '请求时间',
//    request_uri     varchar(255)    NOT NULL                            COMMENT '请求URI',
//    method          varchar(20)     NOT NULL                            COMMENT '操作方式',
//    params          varchar(2000)                                       COMMENT '操作提交的数据',
//    process_time    bigint          NOT NULL                            COMMENT '请求处理时间',
//    remote_addr     varchar(30)                                         COMMENT '客户端的IP地址',
//    user_agent      varchar(100)                                        COMMENT '用户代理',
//    has_exception   char(1)         NOT NULL    DEFAULT '1'             COMMENT '是否有异常（0：否；1：是）',
//    exception_info  varchar(2000)                                       COMMENT '异常信息',

    /**
     * 模糊查询访问日志，使用分页
     *
     * @param page             分页对象
     * @param loginName        登录名
     * @param requestStartTime 请求时间(最小)
     * @param requestEndTime   请求时间(最大)
     * @param requestUri       请求URI
     * @param method           操作方式
     * @param processMinTime   请求处理时间(最小)
     * @param processMaxTime   请求处理时间(最大)
     * @param remoteAddr       客户端的IP地址
     * @param userAgent        用户代理
     * @param hasException     是否有异常（0：否；1：是）
     * @return 分页对象
     */
    public Page<AccessLog> findByPage(Page<AccessLog> page,
                                      String loginName,
                                      Date requestStartTime,
                                      Date requestEndTime,
                                      String requestUri,
                                      String method,
                                      Long processMinTime,
                                      Long processMaxTime,
                                      String remoteAddr,
                                      String userAgent,
                                      Character hasException) {
        Parameter param = new Parameter();
        param.put("loginName", loginName);
        param.put("requestStartTime", requestStartTime);
        param.put("requestEndTime", requestEndTime);
        param.put("requestUri", "%" + requestUri + "%");
        param.put("method", method);
        param.put("processMinTime", processMinTime);
        param.put("processMaxTime", processMaxTime);
        param.put("remoteAddr", "%"+ remoteAddr +"%");
        param.put("userAgent", "%" + userAgent + "%");
        param.put("hasException", hasException);
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.core.dao.AccessLogDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}
