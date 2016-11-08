package org.cleverframe.shiro.shiro;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.cleverframe.common.codec.EncodeDecodeUtils;
import org.cleverframe.common.utils.IDCreateUtils;

/**
 * Shiro 密码工具，用于加密和解密
 * @author LiZhiWei
 * @version 2015年7月17日 下午8:53:54
 */
public class ShiroPasswordUtils
{
	/**
	 * 用户登录密码解密<br>
	 * @param ciphertext 密文
	 * @return 明文
	 * */
	@Deprecated
	public static String Decryption(String ciphertext)
	{
		throw new RuntimeException("用户登录密码使用了非对称加密方式，不能解密！");
	}

	/**
	 * 用户登录密码加密<br>
	 * @param plaintext 明文
	 * @return 密文
	 * @see ShiroAuthorizingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	public static String Encryption(String plaintext)
	{
		if (plaintext == null)
		{
			return "";
		}
		// 16长度的 salt
		String uuid = IDCreateUtils.uuidNotSplit();
		byte[] data = EncodeDecodeUtils.decodeHex(uuid.substring(0, 16));
		ByteSource salt = ByteSource.Util.bytes(data);
		SimpleHash simpleHash = new SimpleHash("SHA-1", plaintext.toCharArray(), salt, 1024);
		return uuid.substring(0, 16) + simpleHash.toString();
	}

	public static void main(String[] args)
	{
		// 123456789012345678889f9965fd00ad021d8770b1cdc2b9ebe7ffe9
		System.out.println(Encryption("12345678"));
	}
}
