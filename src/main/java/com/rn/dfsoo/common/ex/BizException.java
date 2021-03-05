package com.rn.dfsoo.common.ex;

/**
 * Description：应用运行时异常 - 业务异常
 *
 * @author 然诺
 * @date 2019/8/28
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 3420268083230000759L;

	private IExceptionCode errorCode;

	public BizException() {
		super();
	}

	public BizException(String message) {
		super(message);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(IExceptionCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	public IExceptionCode getErrorCode() {
		return errorCode;
	}
}
