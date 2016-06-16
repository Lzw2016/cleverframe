package org.cleverframe.common.user;

/**
 * 用户信息获取接口<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-6-16 10:41 <br/>
 */
public interface IUserUtils {
    /**
     * 获取当前用户的编码(唯一编码)
     *
     * @return 用户的编码(唯一编码)
     */
    String getUserCode();

    /**
     * 获取当前用户所属公司的机构编码
     *
     * @return 所属公司的机构编码
     */
    String getCompanyCode();

    /**
     * 获取当前用户直属机构的编码
     *
     * @return 直属机构的编码
     */
    String getOrgCode();
}
