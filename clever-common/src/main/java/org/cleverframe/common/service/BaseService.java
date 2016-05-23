//package org.cleverframe.common.service;
//
//import org.cleverframe.common.utils.IUserUtils;
//import org.cleverframe.modules.sys.SysBeanNames;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.transaction.annotation.Transactional;
//
///**
// * Service基类<br/>
// * 1.提供IUserUtils，方便获取当前用户的组织架构信息<br/>
// *
// * @author LiZW
// * @version 2015年5月28日 下午2:43:28
// */
//@Transactional(readOnly = true)
//public abstract class BaseService {
//    /** 日志对象 */
//    //private final static Logger logger = LoggerFactory.getLogger(BaseService.class);
//
//    /**
//     * IUserUtils方便获取当前用户的组织架构信息
//     */
//    @Autowired
//    @Qualifier(SysBeanNames.UserUtils)
//    protected IUserUtils userUtils;
//}
