package org.cleverframe.sys.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/10/30 20:01 <br/>
 */
public class UserUpdateVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 数据ID
     */
    @NotNull(message = "数据ID不能为空")
    private Long id;

    /**
     * 删除标记（1：正常；2：删除；3：审核）,以字典表sys_dict.dict_key=‘删除标记’为准'
     */
    private String delFlag;

    /**
     * 备注
     */
    @Length(min = 1, max = 255, message = "备注值长度不能超过255个字符")
    private String remarks;


    /**
     * 归属公司
     */
    @Length(min = 1, max = 255, message = "归属公司值长度不能超过255个字符")
    private String homeCompany;

    /**
     * 直属机构
     */
    @Length(min = 1, max = 255, message = "直属机构值长度不能超过255个字符")
    private String homeOrg;

    /**
     * 密码
     */
    @Length(min = 1, max = 100, message = "密码值长度不能超过100个字符")
    private String password;

    /**
     * 工号
     */
    @Length(min = 1, max = 30, message = "工号值长度不能超过30个字符")
    private String jobNo;

    /**
     * 姓名
     */
    @Length(min = 1, max = 100, message = "姓名值长度不能超过100个字符")
    private String name;

    /**
     * 性别（1：男；2：女）
     */
    private Character sex;

    /**
     * 生日
     */
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
    private Character userType;

    /**
     * 用户帐号状态(1：正常；2：锁定；3：删除)
     */
    private Character accountState;

    /**
     * 用户状态(1：试用；2：在职；3：离职)
     */
    private Character userState;

    public UserUpdateVo() {
    }

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}
