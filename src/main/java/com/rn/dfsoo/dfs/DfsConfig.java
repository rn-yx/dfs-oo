package com.rn.dfsoo.dfs;

import com.rn.dfsoo.dfs.fastdfs.FastDFSClient;
import com.rn.dfsoo.dfs.fastdfs.FastDFSOperator;
import com.rn.dfsoo.dfs.localdfs.LocalDFSOperator;
import com.rn.dfsoo.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * Description: DFS 配置
 *
 * @author 然诺
 * @date 2020/7/15
 */
@Configuration
public class DfsConfig {

	@Resource
	private DfsProperties dfsProperties;
//	@Autowired
//	private FastDFSClient storageClient;

	@Bean
	public DfsOperator dfsOperator() {
        String dfsType = dfsProperties.getDfsType();
        if (DfsTypeEnum.FastDFS.name().equals(dfsType)) {
			return new FastDFSOperator(SpringContextUtils.getBean(FastDFSClient.class), dfsProperties);
		} else if (DfsTypeEnum.LocalDFS.name().equals(dfsType)) {
			return new LocalDFSOperator();
		}
		throw new IllegalArgumentException("DFS文件存储类型配置有误，请检查配置项[dfs-type]是否为LocalDFS、FastDFS之一");
	}

}
