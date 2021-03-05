package com.rn.dfsoo.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件元数据
 *
 * @author 然诺
 * @since 2020-04-13
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("file_meta")
public class FileMeta implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建者ID
     */
    private Long creatorId;

    /**
     * 业务code
     */
    private String bizCode;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件类型（1-图片，2-文本，3-音频，4-视频，5-其他）
     */
    private Integer fileType;

    /**
     * 文件描述
     */
    private String fileDesc;

    /**
     * 文件状态（1-使用中，2-已上链，3-上链失败，4-已删除）
     */
    private Integer fileStatus;

    /**
     * 文件后缀
     */
    private String suffix;

    /**
     * 文件大小
     */
    private Long size;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件MD5哈希值
     */
    private String mdHash;

    /**
     * 文件SHA256哈希值
     */
    private String shaHash;

    /**
     * 存储类型（LocalDFS、FastDFS、HDFS）
     */
    private String storageType;

    /**
     * 是否加密（0：否 1：是）
     */
    private Boolean isEncrypt;

    /**
     * 属性加密后的对称密钥
     */
    private String abeSecretKey;

    /**
     * 是否临时（0：否 1：是）
     */
    private Boolean isTemp;

    /**
     * 逻辑删除（0：未删除 1：删除）
     */
    private Boolean isDeleted;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;
}