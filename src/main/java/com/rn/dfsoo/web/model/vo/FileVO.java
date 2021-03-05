package com.rn.dfsoo.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Description: 文件VO
 *
 * @author 然诺
 * @date 2020/4/23
 */
@Setter
@Getter
@ApiModel(value="文件VO")
public class FileVO extends FileMetaVO {

	@ApiModelProperty(value = "文件字节数组")
	private byte[] fileBytes;

}
