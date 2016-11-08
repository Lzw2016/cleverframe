package org.cleverframe.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 实体类，对应表sys_user(用户表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-08-24 00:14:43 <br/>
 */
@Entity
@Table(name = "sys_user")
@DynamicInsert
@DynamicUpdate
public class User extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    public final static Character ACCOUNT_STATE_NORMAL = '1';

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    public final static Character ACCOUNT_STATE_LOCKED = '2';

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    public final static Character ACCOUNT_STATE_DELETE = '3';

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    public final static Character USER_STATE_TRY = '1';

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    public final static Character USER_STATE_JOB = '2';

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    public final static Character USER_STATE_LEAVE = '3';


    /**
     * 归属公司
     */
    private String homeCompany;

    /**
     * 直属机构
     */
    private String homeOrg;

    /**
     * 登录名，不能修改
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 工号
     */
    private String jobNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    private Character sex;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date birthday;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 用户类型（1：内部用户；2：外部用户）
     */
    private Character userType;

    /**
     * 最后登陆IP
     */
    private String loginIp;

    /**
     * 最后登陆时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date loginDate;

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    private Character accountState;

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    private Character userState;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 归属公司
     */
    public String getHomeCompany() {
        return homeCompany;
    }

    /**
     * 设置 归属公司
     */
    public void setHomeCompany(String homeCompany) {
        this.homeCompany = homeCompany;
    }

    /**
     * 获取 直属机构
     */
    public String getHomeOrg() {
        return homeOrg;
    }

    /**
     * 设置 直属机构
     */
    public void setHomeOrg(String homeOrg) {
        this.homeOrg = homeOrg;
    }

    /**
     * 获取 登录名，不能修改
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 设置 登录名，不能修改
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * 获取 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取 工号
     */
    public String getJobNo() {
        return jobNo;
    }

    /**
     * 设置 工号
     */
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    /**
     * 获取 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取 用户类型（1：内部用户；2：外部用户）
     */
    public Character getUserType() {
        return userType;
    }

    /**
     * 设置 用户类型（1：内部用户；2：外部用户）
     */
    public void setUserType(Character userType) {
        this.userType = userType;
    }

    /**
     * 获取 最后登陆IP
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * 设置 最后登陆IP
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    /**
     * 获取 最后登陆时间
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     * 设置 最后登陆时间
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * 获取 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    public Character getAccountState() {
        return accountState;
    }

    /**
     * 设置 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    public void setAccountState(Character accountState) {
        this.accountState = accountState;
    }

    /**
     * 获取 用户状态(1：试用；2：在职；3：离职)
     */
    public Character getUserState() {
        return userState;
    }

    /**
     * 设置 用户状态(1：试用；2：在职；3：离职)
     */
    public void setUserState(Character userState) {
        this.userState = userState;
    }

}
