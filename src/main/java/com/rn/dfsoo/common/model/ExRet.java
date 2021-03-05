package com.rn.dfsoo.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Description：异常信息对象
 *
 * @author 然诺
 * @date 2019/8/28
 */
@Getter
@Setter
@ApiModel(description = "异常信息对象")
public class ExRet implements Serializable {

	@ApiModelProperty(value = "异常时间", required = true)
	private Date date;

	@ApiModelProperty(value = "异常类名", required = true)
	private String type;

	@ApiModelProperty(value = "异常信息", required = true)
	private String message;

	@ApiModelProperty(value = "异常堆栈", required = true)
	private String stackTrace;

}
