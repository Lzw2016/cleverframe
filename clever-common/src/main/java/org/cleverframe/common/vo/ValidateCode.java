package org.cleverframe.common.vo;

import java.io.Serializable;

/**
 * 验证码信息，存放到Session中用于验证码比对<br/>
 *
 * @author LiZW
 * @version 2015年11月30日 下午2:23:37
 */
public class ValidateCode implements Serializable {
    /**
     * 验证码创建时间
     */
    private long createTime;

    /**
     * 验证码
     */
    private String content;

    public ValidateCode() {

    }

    /**
     * @param createTime 验证码创建时间
     * @param content    验证码
     */
    public ValidateCode(long createTime, String content) {
        this.createTime = createTime;
        this.content = content;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
