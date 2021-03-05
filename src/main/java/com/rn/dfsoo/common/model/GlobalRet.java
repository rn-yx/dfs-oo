package com.rn.dfsoo.common.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.rn.dfsoo.common.constant.GlobalConstants;
import com.rn.dfsoo.common.ex.IExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Description：通用响应对象
 *
 * @author 然诺
 * @date 2019/9/26
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel(description = "通用响应对象")
public class GlobalRet<T> implements Serializable {

	@ApiModelProperty(value = "状态码", required = true)
	private Integer code = GlobalConstants.SUCCESS;

	@ApiModelProperty(value = "描述信息", required = true)
	private String message = GlobalConstants.SUCCESS_DESCRIPTION;

	@ApiModelProperty(value = "结果集(泛型)")
	private T data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@ApiModelProperty(value = "异常信息")
	private ExRet error = null;

	public GlobalRet() {
	}

	/**
	 * 构造函数
	 *
	 * @param data 结果集(泛型)
	 */
	public GlobalRet(T data) {
		this.data = data;
	}


	public GlobalRet(Throwable e) {
		this.message = e.getMessage();
		this.code = GlobalConstants.FAIL;
	}

	public GlobalRet(IExceptionCode code) {
		if (code != null) {
			this.code = code.getStatus();
			this.message = code.getMessage();
		}
	}

	/**
	 * 成功时候的调用
	 */
	public static <T> GlobalRet<T> success() {
		return new GlobalRet<>();
	}

	/**
	 * 成功时候的调用（带数据）
	 */
	public static <T> GlobalRet<T> success(T data) {
		return new GlobalRet<>(data);
	}

	/**
	 * 成功时候的调用（标记、带数据）
	 */
	public static <T> GlobalRet<T> success(boolean flag, T data) {
		if (flag) {
			return new GlobalRet<>(data);
		} else {
			GlobalRet<T> ret = new GlobalRet<>();
			ret.setCode(GlobalConstants.FAIL);
			ret.setMessage(GlobalConstants.FAIL_DESCRIPTION);
			return ret;
		}
	}

	/**
	 * 成功时候的调用
	 */
	public static <T> GlobalRet<T> failure() {
		GlobalRet<T> ret = new GlobalRet<>();
		ret.setCode(GlobalConstants.FAIL);
		ret.setMessage(GlobalConstants.FAIL_DESCRIPTION);
		return ret;
	}

	/**
	 * 失败时候的调用
	 */
	public static <T> GlobalRet<T> failure(IExceptionCode iExceptionEnum) {
		return new GlobalRet<>(iExceptionEnum);
	}

	/**
	 * 是否成功
	 *
	 * @return true/false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isSuccess() {
		return GlobalConstants.SUCCESS == this.code;
	}

	/**
	 * 是否失败
	 *
	 * @return true/false
	 */
	@JsonIgnore
	@JSONField(serialize = false)
	public boolean isFail() {
		return !isSuccess();
	}

	/**
	 * 提取服务调用返回结果，若返回失败，则抛出业务指定的异常
	 *
	 * @param s
	 * @param <X>
	 * @return
	 * @throws X
	 */
	public <X extends Throwable> T orElseThrow(Supplier<? extends X> s) throws X {
		if (this.isSuccess()) {
			return this.getData();
		} else {
			throw s.get();
		}
	}

}
