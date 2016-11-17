package org.cleverframe.filemanager.entity;

import org.cleverframe.core.persistence.entity.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 实体类，对应表filemanager_ftp_user(FTP服务器用户)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-11-17 21:46:41 <br/>
 */
@Entity
@Table(name = "filemanager_ftp_user")
@DynamicInsert
@DynamicUpdate
public class FtpUser implements BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.TABLE)
    private String userid;

    /**
     * 用户密码
     */
    private String userpassword;

    /**
     * 主目录
     */
    private String homedirectory;

    /**
     * 当前用户可用
     */
    private Byte enableflag;

    /**
     * 具有上传权限
     */
    private Byte writepermission;

    /**
     * 空闲时间
     */
    private Integer idletime;

    /**
     * 上传速率限制（字节每秒）
     */
    private Integer uploadrate;

    /**
     * 下载速率限制（字节每秒）
     */
    private Integer downloadrate;

    /**
     * 最大登陆用户数
     */
    private Integer maxloginnumber;

    /**
     * 同IP登陆用户数
     */
    private Integer maxloginperip;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 用户ID
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置 用户ID
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取 用户密码
     */
    public String getUserpassword() {
        return userpassword;
    }

    /**
     * 设置 用户密码
     */
    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    /**
     * 获取 主目录
     */
    public String getHomedirectory() {
        return homedirectory;
    }

    /**
     * 设置 主目录
     */
    public void setHomedirectory(String homedirectory) {
        this.homedirectory = homedirectory;
    }

    /**
     * 获取 当前用户可用
     */
    public Byte getEnableflag() {
        return enableflag;
    }

    /**
     * 设置 当前用户可用
     */
    public void setEnableflag(Byte enableflag) {
        this.enableflag = enableflag;
    }

    /**
     * 获取 具有上传权限
     */
    public Byte getWritepermission() {
        return writepermission;
    }

    /**
     * 设置 具有上传权限
     */
    public void setWritepermission(Byte writepermission) {
        this.writepermission = writepermission;
    }

    /**
     * 获取 空闲时间
     */
    public Integer getIdletime() {
        return idletime;
    }

    /**
     * 设置 空闲时间
     */
    public void setIdletime(Integer idletime) {
        this.idletime = idletime;
    }

    /**
     * 获取 上传速率限制（字节每秒）
     */
    public Integer getUploadrate() {
        return uploadrate;
    }

    /**
     * 设置 上传速率限制（字节每秒）
     */
    public void setUploadrate(Integer uploadrate) {
        this.uploadrate = uploadrate;
    }

    /**
     * 获取 下载速率限制（字节每秒）
     */
    public Integer getDownloadrate() {
        return downloadrate;
    }

    /**
     * 设置 下载速率限制（字节每秒）
     */
    public void setDownloadrate(Integer downloadrate) {
        this.downloadrate = downloadrate;
    }

    /**
     * 获取 最大登陆用户数
     */
    public Integer getMaxloginnumber() {
        return maxloginnumber;
    }

    /**
     * 设置 最大登陆用户数
     */
    public void setMaxloginnumber(Integer maxloginnumber) {
        this.maxloginnumber = maxloginnumber;
    }

    /**
     * 获取 同IP登陆用户数
     */
    public Integer getMaxloginperip() {
        return maxloginperip;
    }

    /**
     * 设置 同IP登陆用户数
     */
    public void setMaxloginperip(Integer maxloginperip) {
        this.maxloginperip = maxloginperip;
    }

}