package com.rn.dfsoo.common.model;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * Description: 分页模型Query基类（注意：分页查询必须继承此类）
 *
 * @author 然诺
 * @date 2019/8/28
 */
@Getter
@Setter
@ApiModel(value = "分页模型")
public abstract class BasePageQuery implements Serializable {

	@ApiModelProperty(value = "当前页<br />默认值为1，最小值为1", example = "1")
	@Min(value = 1, message = "当前页不能小于1")
	private Integer current = 1;

	@ApiModelProperty(value = "分页大小<br />默认值为10，最小值为3，最大值为30", example = "10")
	@Min(value = 3, message = "分页大小不能小于3")
	@Max(value = 30, message = "分页大小不能大于30")
	private Integer size = 10;

	@ApiModelProperty(value = "排序属性<br />默认值为主键id", allowEmptyValue = true, example = "id")
	private String property = "id";

	@ApiModelProperty(value = "排序方向<br />ASC表示升序，DESC表示降序", allowableValues = "ASC, DESC", allowEmptyValue = true, example = "ASC")
	private String direction = "ASC";

	/**
	 * 转换为 Mybatis Plus 分页对象
	 */
	@ApiIgnore
	public Page page() {
		Page page = new Page();
		page.setCurrent(this.current);
		page.setSize(this.size);
		if ("DESC".equalsIgnoreCase(this.direction)) {
			page.setDesc(this.property);
		} else {
			page.setAsc(this.property);
		}
		return page;
	}

}
