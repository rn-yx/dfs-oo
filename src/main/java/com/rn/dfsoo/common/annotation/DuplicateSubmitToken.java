package com.rn.dfsoo.common.annotation;

import java.lang.annotation.*;

/**
 * Description: 防止表单重复提交注解（单JVM版本）
 *
 * @author 然诺
 * @date 2019/10/17
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DuplicateSubmitToken {
	/**
	 * 单次请求级别
	 */
	public static final int REQUEST = 1;
	/**
	 * 会话级别
	 */
	public static final int SESSION = 2;

	/**
	 * 保存重复提交标记（默认需要保存）
	 */
	boolean save() default true;

	/**
	 * 防止重复提交类型，默认：单次请求级别
	 */
	int type() default REQUEST;
}
