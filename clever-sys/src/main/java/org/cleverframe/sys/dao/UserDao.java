package org.cleverframe.sys.dao;


import org.cleverframe.common.persistence.Page;
import org.cleverframe.common.persistence.Parameter;
import org.cleverframe.core.persistence.dao.BaseDao;
import org.cleverframe.core.utils.QLScriptUtils;
import org.cleverframe.sys.SysBeanNames;
import org.cleverframe.sys.entity.User;
import org.cleverframe.sys.vo.request.UserQueryVo;
import org.springframework.stereotype.Repository;

/**
 * DAO，对应表sys_user(用户表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 22:29:06 <br/>
 */
@Repository(SysBeanNames.UserDao)
public class UserDao extends BaseDao<User> {

    /**
     * 分页查询
     */
    public Page<User> findByPage(Page<User> page, UserQueryVo userQueryVo) {
        Parameter param = new Parameter();
        param.put("delFlag", userQueryVo.getDelFlag());
        param.put("homeCompany", userQueryVo.getHomeCompany());
        param.put("homeOrg", userQueryVo.getHomeOrg());
        param.put("loginName", userQueryVo.getLoginName());
        param.put("jobNo", userQueryVo.getJobNo());
        param.put("name", userQueryVo.getName());
        param.put("sex", userQueryVo.getSex());
        param.put("birthdayStart", userQueryVo.getBirthdayStart());
        param.put("birthdayEnd", userQueryVo.getBirthdayEnd());
        param.put("email", userQueryVo.getEmail());
        param.put("phone", userQueryVo.getPhone());
        param.put("mobile", userQueryVo.getMobile());
        param.put("userType", userQueryVo.getUserType());
        param.put("accountState", userQueryVo.getAccountState());
        param.put("userState", userQueryVo.getUserState());
        String sql = QLScriptUtils.getSQLScript("org.cleverframe.sys.dao.UserDao.findByPage");
        return hibernateDao.findBySql(page, sql, param);
    }
}
