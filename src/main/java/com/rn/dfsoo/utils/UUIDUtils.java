package com.rn.dfsoo.utils;

import java.util.UUID;

/**
 * Description: UUID工具类
 *
 * @author 然诺
 * @date 2020/07/16
 */
public class UUIDUtils {

	public static String genUuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

}
