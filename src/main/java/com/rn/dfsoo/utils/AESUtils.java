package com.rn.dfsoo.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Objects;

/**
 * Description: AES加密工具
 *
 * @author 然诺
 * @date 2020/3/30
 */
public class AESUtils {
	/**
	 * 加密密钥
	 */
	private static final String DEFAULT_PASS = "**&..%!";
	/**
	 * 加密方式
	 */
	private static final String ENCRYPTION = "AES";

	/**
	 * AES加密（使用默认秘钥）
	 *
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] content) throws Exception {
		return aes(content, DEFAULT_PASS, Cipher.ENCRYPT_MODE);
	}


	/**
	 * AES解密（使用默认秘钥）
	 *
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] content) throws Exception {
		return aes(content, DEFAULT_PASS, Cipher.DECRYPT_MODE);
	}

	/**
	 * AES加密（使用默认秘钥）
	 *
	 * @param content 明文
	 * @return 十六进制密文
	 */
	public static String encrypt(String content) throws Exception {
		byte[] byteContent = content.getBytes(StandardCharsets.UTF_8);
		return HexUtils.byte2Hex(aes(byteContent, DEFAULT_PASS, Cipher.ENCRYPT_MODE));
	}


	/**
	 * AES解密（使用默认秘钥）
	 *
	 * @param content 十六进制密文
	 * @return 明文
	 */
	public static String decrypt(String content) throws Exception {
		byte[] byteContent = HexUtils.hex2Byte(content);
		return new String(aes(byteContent, DEFAULT_PASS, Cipher.DECRYPT_MODE));
	}

	/**
	 * AES加密
	 *
	 * @param content  待加密内容
	 * @param password 密钥
	 * @return
	 */
	public static byte[] encrypt(byte[] content, String password) throws Exception {
		return aes(content, password, Cipher.ENCRYPT_MODE);
	}


	/**
	 * AES解密
	 *
	 * @param content  待解密内容
	 * @param password 密钥
	 */
	public static byte[] decrypt(byte[] content, String password) throws Exception {
		return aes(content, password, Cipher.DECRYPT_MODE);
	}

	/**
	 * AES加密/解密
	 *
	 * @param content  待加密内容
	 * @param password 密钥
	 * @param type     加密/解密
	 */
	private static byte[] aes(byte[] content, String password, int type) throws Exception {
		KeyGenerator generator = KeyGenerator.getInstance(ENCRYPTION);
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		generator.init(128, random);
		SecretKey secretKey = generator.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, ENCRYPTION);
		Cipher cipher = Cipher.getInstance(ENCRYPTION);
		cipher.init(type, key);
		if (type == Cipher.ENCRYPT_MODE) {
			return cipher.doFinal(content);
		} else {
			return cipher.doFinal(Objects.requireNonNull(content));
		}
	}

}
