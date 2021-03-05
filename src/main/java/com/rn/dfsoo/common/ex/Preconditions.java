package com.rn.dfsoo.common.ex;

/**
 * Description: 前提条件验证（不满足则抛出去异常）
 *
 * @author 然诺
 * @date 2020/02/26
 */
public final class Preconditions {

	private Preconditions() {
	}

	public static <T> void checkNull(T reference, IExceptionCode exCode) {
		if (reference != null) {
			throw new BizException(exCode);
		}
	}

	public static <T> T checkNotNull(T reference, IExceptionCode exCode) {
		if (reference == null) {
			throw new BizException(exCode);
		} else {
			return reference;
		}
	}

	public static void checkTrue(boolean expression, IExceptionCode exCode) {
		if (!expression) {
			throw new BizException(exCode);
		}
	}

	public static void checkFalse(boolean expression, IExceptionCode exCode) {
		if (expression) {
			throw new BizException(exCode);
		}
	}
}
