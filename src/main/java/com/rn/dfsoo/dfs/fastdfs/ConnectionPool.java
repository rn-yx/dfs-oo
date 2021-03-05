package com.rn.dfsoo.dfs.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.ProtoCommon;
import org.csource.fastdfs.TrackerServer;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.util.Properties;

/**
 * Description: FastDFS 连接池
 *
 * @author fdfs
 * @date 2020/10/21
 */
@Slf4j
public class ConnectionPool implements InitializingBean {
	private GenericObjectPool<TrackerServer> trackerServerPool;
	private FastDFSProperties properties;

	private ConnectionPool() {
	}

	public ConnectionPool(FastDFSProperties properties) {
		this.properties = properties;
		Properties prop = this.properties.buildProperties();
		try {
			ClientGlobal.initByProperties(prop);
		} catch (IOException | MyException e) {
			e.printStackTrace();
		}
	}

	public TrackerServer borrowObject() {
		TrackerServer trackerServer = null;
		try {
			trackerServer = trackerServerPool.borrowObject();
			ProtoCommon.activeTest(trackerServer.getSocket());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trackerServer;
	}

	public void returnObject(TrackerServer trackerServer) {
		try {
			trackerServerPool.returnObject(trackerServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void afterPropertiesSet() {
		ConnectionPoolFactory factory = new ConnectionPoolFactory();
		GenericObjectPoolConfig<TrackerServer> poolConfig = new PoolBuilderFactory().getPoolConfig(properties.getPool());
		trackerServerPool = new GenericObjectPool<>(factory, poolConfig);
	}

	private static class PoolBuilderFactory {
		private PoolBuilderFactory() {
		}

		private GenericObjectPoolConfig<TrackerServer> getPoolConfig(FastDFSProperties.Pool properties) {
			GenericObjectPoolConfig<TrackerServer> config = new GenericObjectPoolConfig<>();
			config.setMaxTotal(properties.getMaxActive());
			config.setMaxIdle(properties.getMaxIdle());
			config.setMinIdle(properties.getMinIdle());
			if (properties.getTimeBetweenEvictionRuns() != null) {
				config.setTimeBetweenEvictionRunsMillis(properties.getTimeBetweenEvictionRuns().toMillis());
			}

			if (properties.getMaxWait() != null) {
				config.setMaxWaitMillis(properties.getMaxWait().toMillis());
			}
			return config;
		}
	}

}
