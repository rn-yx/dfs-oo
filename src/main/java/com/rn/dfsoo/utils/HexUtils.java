package com.rn.dfsoo.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

/**
 * Description: 二进制转换工具类
 *
 * @author 然诺
 * @date 2020/3/30
 */
public class HexUtils {

	/**
	 * 二进位组转十六进制字符串
	 *
	 * @param bytes 二进位组
	 * @return 十六进制字符串
	 */
	public static String byte2Hex(byte[] bytes) {
		Hex hex = new Hex();
		byte[] encode = hex.encode(bytes);
		return new String(encode);
	}

	/**
	 * 十六进制字符串转二进位组
	 *
	 * @param hexStr 十六进制字符串
	 * @return 二进位组
	 */
	public static byte[] hex2Byte(String hexStr) throws DecoderException {
		Hex hex = new Hex();
		return hex.decode(hexStr.getBytes());
	}

}
