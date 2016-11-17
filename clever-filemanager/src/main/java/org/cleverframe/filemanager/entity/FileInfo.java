package org.cleverframe.filemanager.entity;

import org.cleverframe.core.persistence.entity.DataEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 实体类，对应表filemanager_file_info(上传文件信息表)<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-11-17 21:42:42 <br/>
 */
@Entity
@Table(name = "filemanager_file_info")
@DynamicInsert
@DynamicUpdate
public class FileInfo extends DataEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：FastDFS服务器）
     */
    public static final char LOCAL_STORAGE = '1';
    /**
     * 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：FastDFS服务器）
     */
    public static final char FTP_STORAGE = '2';
    /**
     * 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：FastDFS服务器）
     */
    public static final char FASTDFS_STORAGE = '3';

    /**
     * 文件签名算法类型（1：MD5；2：SHA-1；）
     */
    public static final char MD5_DIGEST = '1';
    /**
     * 文件签名算法类型（1：MD5；2：SHA-1；）
     */
    public static final char SHA1_DIGEST = '2';

    /**
     * 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    private Character storedType;

    /**
     * 上传文件存放路径
     */
    private String filePath;

    /**
     * 文件签名，用于判断是否是同一文件
     */
    private String digest;

    /**
     * 文件签名算法类型（1：MD5；2：SHA-1；）
     */
    private Character digestType;

    /**
     * 文件大小，单位：byte(1KB = 1024byte)
     */
    private Long fileSize;

    /**
     * 文件原名称，用户上传时的名称
     */
    private String fileName;

    /**
     * 文件当前名称（UUID + 后缀名）
     */
    private String newName;

    /**
     * 文件上传所用时间
     */
    private Long uploadTime;

    /**
     * 文件存储用时，单位：毫秒（此时间只包含服务器端存储文件所用的时间，不包括文件上传所用的时间）
     */
    private Long storedTime;

    /**
     * 文件来源（可以是系统模块名）
     */
    private String fileSource;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    /**
     * 获取 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public Character getStoredType() {
        return storedType;
    }

    /**
     * 设置 上传文件的存储类型（1：当前服务器硬盘；2：FTP服务器；3：；FastDFS服务器）
     */
    public void setStoredType(Character storedType) {
        this.storedType = storedType;
    }

    /**
     * 获取 上传文件存放路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置 上传文件存放路径
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * 获取 文件签名，用于判断是否是同一文件
     */
    public String getDigest() {
        return digest;
    }

    /**
     * 设置 文件签名，用于判断是否是同一文件
     */
    public void setDigest(String digest) {
        this.digest = digest;
    }

    /**
     * 获取 文件签名算法类型（1：MD5；2：SHA-1；）
     */
    public Character getDigestType() {
        return digestType;
    }

    /**
     * 设置 文件签名算法类型（1：MD5；2：SHA-1；）
     */
    public void setDigestType(Character digestType) {
        this.digestType = digestType;
    }

    /**
     * 获取 文件大小，单位：byte(1KB = 1024byte)
     */
    public Long getFileSize() {
        return fileSize;
    }

    /**
     * 设置 文件大小，单位：byte(1KB = 1024byte)
     */
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    /**
     * 获取 文件原名称，用户上传时的名称
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置 文件原名称，用户上传时的名称
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 获取 文件当前名称（UUID + 后缀名）
     */
    public String getNewName() {
        return newName;
    }

    /**
     * 设置 文件当前名称（UUID + 后缀名）
     */
    public void setNewName(String newName) {
        this.newName = newName;
    }

    /**
     * 获取 文件上传所用时间
     */
    public Long getUploadTime() {
        return uploadTime;
    }

    /**
     * 设置 文件上传所用时间
     */
    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    /**
     * 获取 文件存储用时，单位：毫秒（此时间只包含服务器端存储文件所用的时间，不包括文件上传所用的时间）
     */
    public Long getStoredTime() {
        return storedTime;
    }

    /**
     * 设置 文件存储用时，单位：毫秒（此时间只包含服务器端存储文件所用的时间，不包括文件上传所用的时间）
     */
    public void setStoredTime(Long storedTime) {
        this.storedTime = storedTime;
    }

    /**
     * 获取 文件来源（可以是系统模块名）
     */
    public String getFileSource() {
        return fileSource;
    }

    /**
     * 设置 文件来源（可以是系统模块名）
     */
    public void setFileSource(String fileSource) {
        this.fileSource = fileSource;
    }
}