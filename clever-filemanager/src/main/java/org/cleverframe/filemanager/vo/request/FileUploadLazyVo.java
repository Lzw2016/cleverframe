package org.cleverframe.filemanager.vo.request;

import org.cleverframe.common.vo.request.BaseRequestVo;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016/11/18 10:17 <br/>
 */
public class FileUploadLazyVo extends BaseRequestVo {
    private static final long serialVersionUID = 1L;

    /**
     * 上传文件原名称
     */
    @NotBlank(message = "上传文件原名称不能为空")
    private String fileName;

    /**
     * 上传文件的文件签名
     */
    @NotBlank(message = "上传文件的文件签名不能为空")
    private String fileDigest;

    /**
     * 文件签名类型，目前只支持MD5和SHA1两种（1：MD5；2：SHA-1；）
     */
    @Pattern(regexp = "1|2", message = "文件签名类型只能是：1(MD5)、2(SHA-1)")
    private String digestType;

    /*--------------------------------------------------------------
     *          getter、setter
     * -------------------------------------------------------------*/

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDigest() {
        return fileDigest;
    }

    public void setFileDigest(String fileDigest) {
        this.fileDigest = fileDigest;
    }

    public String getDigestType() {
        return digestType;
    }

    public void setDigestType(String digestType) {
        this.digestType = digestType;
    }
}
