package com.rn.dfsoo.common.mvc;

import com.rn.dfsoo.common.ex.IExceptionCode;
import com.rn.dfsoo.common.model.GlobalRet;

/**
 * Description: Controller 基类
 *
 * @author 然诺
 * @date 2019/8/28
 */
public abstract class BaseController {

	protected static GlobalRet success() {
		return GlobalRet.success();
	}

	protected static <T> GlobalRet<T> success(T data) {
		return GlobalRet.success(data);
	}

	protected static <T> GlobalRet<T> error() {
		return GlobalRet.failure();
	}

	protected static <T> GlobalRet<T> error(IExceptionCode code) {
		return GlobalRet.failure(code);
	}
}
