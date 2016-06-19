package org.cleverframe.common.service;

import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.user.IUserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service基类<br/>
 * 1.提供IUserUtils，方便获取当前用户的组织架构信息<br/>
 *
 * @author LiZW
 * @version 2015年5月28日 下午2:43:28
 */
@Transactional(readOnly = true)
public abstract class BaseService {
    /** 日志对象 */
    private final static Logger logger = LoggerFactory.getLogger(BaseService.class);

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

}
