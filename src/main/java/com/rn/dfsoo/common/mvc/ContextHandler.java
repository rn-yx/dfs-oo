package com.rn.dfsoo.common.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description: 基础上下文处理器
 *
 * @author 然诺
 * @date 2020/3/25
 */
public class ContextHandler {
	private static final Logger log = LoggerFactory.getLogger(ContextHandler.class);

	public static final String CURRENT_USER = "C_U";
	public static final String ACCESS_TOKEN = "A_T";
	public static final String LOG = "LOG";
	public static final String OPEN_API_LOG = "O_A_L";

	private static final ThreadLocal<Map<String, Object>> THREAD_LOCAL = new ThreadLocal<>();

	private ContextHandler() {
	}

	public static void set(String key, Object value) {
		getThreadLocal().put(key, value);
	}

	public static <T> T get(String key) {
		Object o = getThreadLocal().get(key);
		if (o != null) {
			return (T) o;
		}
		return null;
	}

//	/**
//	 * 是否已认证
//	 *
//	 * @return true/false
//	 */
//	public static boolean isAuthenticated() {
//		Object obj = get(CURRENT_USER);
//		if (obj == null) {
//			return false;
//		}
//		return obj instanceof ShiroAccount;
//	}
//
//	/**
//	 * 获取当前登录用户
//	 *
//	 * @return 获取用户信息的对象
//	 */
//	public static ShiroAccount getCurrentUser() {
//		return getCurrentUser(true);
//	}

	/**
	 * 获取线程变量Map
	 */
	private static Map<String, Object> getThreadLocal() {
		Map<String, Object> map = THREAD_LOCAL.get();
		if (map == null) {
			map = new ConcurrentHashMap<>(16);
			THREAD_LOCAL.set(map);
		}
		return THREAD_LOCAL.get();
	}

	/**
	 * 移除
	 */
	public static void remove() {
		THREAD_LOCAL.remove();
	}

	/**
	 * 记录日志
	 *
	 * @param obj 日志对象
	 */
	public static void log(Object obj) {
		set(LOG, obj);
	}

}
