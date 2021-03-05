package com.rn.dfsoo.common.ex;

/**
 * 描述：异常错误枚举编码接口
 * 定义了异常枚举数据访问接口。自定义异常枚举类都需要实现此接口，以适应全局异常处理
 *
 * @author 然诺
 * @date 2019/8/28
 */
public interface IExceptionCode {

    /**
     * 获取状态码
     *
     * @return 状态码：包含基础异常码和自定义码
     */
    int getStatus();

    /**
	 * 获取异常消息
	 *
	 * @return 异常提示
	 */
	String getMessage();

    /**
     * 获取编码
     *
     * @return 编码枚举名称
     */
    String getCode();
}
