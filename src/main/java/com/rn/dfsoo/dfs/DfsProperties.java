package com.rn.dfsoo.dfs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Description: 文件系统配置属性
 *
 * @author 然诺
 * @date 2020/10/21
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "dfs")
public class DfsProperties {
	/**
	 * 文件存储类型（可选值：LocalDFS、FastDFS）
	 */
	private String dfsType = DfsTypeEnum.LocalDFS.name();
	/**
	 * 分组名称
	 */
	private String groupName = "group1";
	/**
	 * 是否加密
	 */
	private boolean enableEncrypt = false;
	/**
	 * 加密密钥
	 */
	private String secretKey = "FP123";
	/**
	 * 最大文件大小（默认：100M）
	 */
	private int maxFileSize = 100 * 1024 * 1024;

}
