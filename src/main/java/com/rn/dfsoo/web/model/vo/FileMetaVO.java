package com.rn.dfsoo.web.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 文件元数据VO
 *
 * @author 然诺
 * @since 2020-04-13
 */
@Getter
@Setter
@ToString
@ApiModel(description="文件元数据视图对象")
public class FileMetaVO implements Serializable {
    private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "文件ID")
	private Long id;

    @ApiModelProperty(value = "创建者ID")
    private Long creatorId;

    @ApiModelProperty(value = "业务code")
    private String bizCode;

    @ApiModelProperty(value = "文件名称")
    private String fileName;

    @ApiModelProperty(value = "文件类型（1-图片，2-文本，3-音频，4-视频，5-其他）")
    private Integer fileType;

    @ApiModelProperty(value = "文件描述")
    private String fileDesc;

    @ApiModelProperty(value = "文件状态（1-使用中，2-已上链，3-上链失败，4-已删除）")
    private Integer fileStatus;

    @ApiModelProperty(value = "文件后缀")
    private String suffix;

    @ApiModelProperty(value = "文件大小")
    private Long size;

    @ApiModelProperty(value = "文件路径")
    private String path;

    @ApiModelProperty(value = "文件MD5哈希值")
    private String mdHash;

    @ApiModelProperty(value = "文件SHA256哈希值")
    private String shaHash;

    @ApiModelProperty(value = "存储类型（Local、FastDFS、HDFS）")
    private String storageType;

    @ApiModelProperty(value = "是否加密（0：否 1：是）")
    private Boolean isEncrypt;

    @ApiModelProperty(value = "是否临时（0：否 1：是）")
    private Boolean isTemp;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}