package org.cleverframe.sys.shiro;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.spring.SpringBeanNames;
import org.cleverframe.common.spring.SpringContextHolder;
import org.cleverframe.common.utils.IDCreateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shiro 密码工具，用于加密和解密
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016/11/9 13:58 <br/>
 */
public class ShiroPasswordUtils {
    /**
     * 日志记录器
     */
    private final static Logger logger = LoggerFactory.getLogger(ShiroPasswordUtils.class);

    /**
     * 用户登录密码解密<br>
     *
     * @param ciphertext 密文
     * @return 明文
     */
    @Deprecated
    public static String Decryption(String ciphertext) {
        throw new RuntimeException("用户登录密码使用了非对称加密方式，不能解密！");
    }

    /**
     * 用户登录密码加密<br>
     *
     * @param plaintext 明文
     * @return 密文
     * @see ShiroAuthorizingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
     */
    public static String encryption(String plaintext) {
        if (plaintext == null) {
            return "";
        }

        HashedCredentialsMatcher hashedCredentialsMatcher = SpringContextHolder.getBean(SpringBeanNames.HashedCredentialsMatcher);
        if (hashedCredentialsMatcher == null) {
            throw new RuntimeException("获取注入Bean失败，BeanName=" + SpringBeanNames.LoginFormAuthenticationFilter);
        }

        // 16长度的 salt
        String uuid = IDCreateUtils.uuidNotSplit();
        byte[] data = EncodeDecodeUtils.decodeHex(uuid.substring(0, 16));
        ByteSource salt = ByteSource.Util.bytes(data);
        SimpleHash simpleHash = new SimpleHash(
                hashedCredentialsMatcher.getHashAlgorithmName(),
                plaintext.toCharArray(),
                salt,
                hashedCredentialsMatcher.getHashIterations());
        return uuid.substring(0, 16) + simpleHash.toString();
    }
}
