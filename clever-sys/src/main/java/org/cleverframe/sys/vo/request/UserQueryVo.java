package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;

import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/30 19:32 <br/>
 */
public class UserQueryVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    private Character delFlag;

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
    private Date birthdayStart;

    /**
     * 生日
     */
    private Date birthdayEnd;

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

    public Character getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Character delFlag) {
        this.delFlag = delFlag;
    }

    public String getHomeCompany() {
        return homeCompany;
    }

    public void setHomeCompany(String homeCompany) {
        this.homeCompany = homeCompany;
    }

    public String getHomeOrg() {
        return homeOrg;
    }

    public void setHomeOrg(String homeOrg) {
        this.homeOrg = homeOrg;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Date getBirthdayStart() {
        return birthdayStart;
    }

    public void setBirthdayStart(Date birthdayStart) {
        this.birthdayStart = birthdayStart;
    }

    public Date getBirthdayEnd() {
        return birthdayEnd;
    }

    public void setBirthdayEnd(Date birthdayEnd) {
        this.birthdayEnd = birthdayEnd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Character getUserType() {
        return userType;
    }

    public void setUserType(Character userType) {
        this.userType = userType;
    }

    public Character getAccountState() {
        return accountState;
    }

    public void setAccountState(Character accountState) {
        this.accountState = accountState;
    }

    public Character getUserState() {
        return userState;
    }

    public void setUserState(Character userState) {
        this.userState = userState;
    }
}
