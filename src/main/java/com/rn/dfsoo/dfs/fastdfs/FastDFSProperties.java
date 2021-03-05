package com.rn.dfsoo.dfs.fastdfs;

import lombok.Getter;
import lombok.Setter;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Properties;

/**
 * Description: FastDFS 配置属性（参考：ClientGlobal）
 *
 * @author 然诺
 * @date 2020/10/21
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dfs.fastdfs")
public class FastDFSProperties {
	/**
	 * Tracker Server地址（多个地址时以逗号分隔）
	 */
	private String trackerServers;

	/**
	 * 连接超时间（单位：秒）
	 */
	private int connectTimeoutInSeconds = ClientGlobal.DEFAULT_CONNECT_TIMEOUT;

	/**
	 * 网络超时时间（单位：秒）
	 */
	private int networkTimeoutInSeconds = ClientGlobal.DEFAULT_NETWORK_TIMEOUT;

	/**
	 * 编码
	 */
	private String charset = ClientGlobal.DEFAULT_CHARSET;

	/**
	 * 开启防盗链功能
	 */
	private boolean httpAntiStealToken = false;

	/**
	 * 密钥
	 */
	private String httpSecretKey = ClientGlobal.DEFAULT_HTTP_SECRET_KEY;

	/**
	 * TrackerServer port
	 */
	private int httpTrackerHttpPort = ClientGlobal.DEFAULT_HTTP_TRACKER_HTTP_PORT;

	/**
	 * 连接池
	 */
	private FastDFSProperties.Pool pool = new FastDFSProperties.Pool();

	Properties buildProperties() {
		Properties props = new Properties();
		props.setProperty("fastdfs.tracker_servers", trackerServers);
		props.setProperty("fastdfs.connect_timeout_in_seconds", String.valueOf(connectTimeoutInSeconds));
		props.setProperty("fastdfs.network_timeout_in_seconds", String.valueOf(networkTimeoutInSeconds));
		props.setProperty("fastdfs.charset", charset);
		props.setProperty("fastdfs.http_anti_steal_token", String.valueOf(httpAntiStealToken));
		props.setProperty("fastdfs.http_secret_key", httpSecretKey);
		props.setProperty("fastdfs.http_tracker_http_port", String.valueOf(httpTrackerHttpPort));
		return props;
	}

	public static class Pool {
		private int maxIdle = 8;
		private int minIdle = 0;
		private int maxActive = 8;
		private Duration maxWait = Duration.ofMillis(-1L);
		private Duration timeBetweenEvictionRuns;

		public Pool() {
		}

		public int getMaxIdle() {
			return this.maxIdle;
		}

		public void setMaxIdle(int maxIdle) {
			this.maxIdle = maxIdle;
		}

		public int getMinIdle() {
			return this.minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return this.maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public Duration getMaxWait() {
			return this.maxWait;
		}

		public void setMaxWait(Duration maxWait) {
			this.maxWait = maxWait;
		}

		public Duration getTimeBetweenEvictionRuns() {
			return this.timeBetweenEvictionRuns;
		}

		public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
			this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
		}
	}

}
