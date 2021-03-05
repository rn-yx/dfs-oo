package com.rn.dfsoo.common.ex;

/**
 * Description：防止表单重复提交异常
 *
 * @author 然诺
 * @date 2019/8/28
 */
public class DuplicateSubmitException extends BizException {

    public DuplicateSubmitException(String msg){
        super(msg);
    }

	public DuplicateSubmitException(IExceptionCode exceptionCode) {
		super(exceptionCode);
	}
}
