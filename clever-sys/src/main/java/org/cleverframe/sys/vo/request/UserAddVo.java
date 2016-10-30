package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/30 19:35 <br/>
 */
public class UserAddVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 归属公司
     */
    @NotBlank(message = "归属公司不能为空")
    @Length(min = 1, max = 255, message = "归属公司值长度不能超过255个字符")
    private String homeCompany;

    /**
     * 直属机构
     */
    @NotBlank(message = "直属机构不能为空")
    @Length(min = 1, max = 255, message = "直属机构值长度不能超过255个字符")
    private String homeOrg;

    /**
     * 登录名，不能修改
     */
    @NotBlank(message = "登录名不能为空")
    @Length(min = 1, max = 100, message = "登录名值长度不能超过100个字符")
    private String loginName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Length(min = 1, max = 100, message = "密码值长度不能超过100个字符")
    private String password;

    /**
     * 工号
     */
    @NotBlank(message = "工号不能为空")
    @Length(min = 1, max = 30, message = "工号值长度不能超过30个字符")
    private String jobNo;

    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Length(min = 1, max = 100, message = "姓名值长度不能超过100个字符")
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    @NotNull(message = "性别不能为空")
    private Character sex;

    /**
     * 生日
     */
    @NotNull(message = "生日不能为空")
    private Date birthday;

    /**
     * 邮箱
     */
    @Length(min = 1, max = 100, message = "邮箱值长度不能超过100个字符")
    private String email;

    /**
     * 电话
     */
    @Length(min = 1, max = 100, message = "电话值长度不能超过100个字符")
    private String phone;

    /**
     * 手机
     */
    @Length(min = 1, max = 100, message = "手机号长度不能超过100个字符")
    private String mobile;

    /**
     * 用户类型（1：内部用户；2：外部用户）
     */
    @NotNull(message = "用户类型不能为空")
    private Character userType;

    /**
     * 最后登陆IP
     */
    @Length(min = 1, max = 100, message = "最后登陆IP值长度不能超过100个字符")
    private String loginIp;

    /**
     * 最后登陆时间
     */
    private Date loginDate;

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    @NotNull(message = "用户帐号状态不能为空")
    private Character accountState;

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    @NotNull(message = "用户状态不能为空")
    private Character userState;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
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

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
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
