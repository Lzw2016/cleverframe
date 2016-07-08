package org.cleverframe.common.attributes;

/**
 * 定义Request范围的属性值的名称常量字符串<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-17 11:33 <br/>
 */
public class CommonRequestAttributes implements IRequestAttributes {
    /**
     * 请求开始时间,类型：long(用于计算请求处理用时)
     */
    public static final String REQUEST_START_TIME = "Common_Request_Start_Time";

    /**
     * 服务器异常信息
     */
    public static final String SERVER_EXCEPTION = "Common_Server_Exception";
}
