package com.rn.dfsoo.dfs.fastdfs;

import lombok.extern.slf4j.Slf4j;
import org.csource.fastdfs.TrackerClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description: FastDFS 自动配置
 *
 * @author 然诺
 * @date 2020/10/21
 */
@Slf4j
@Configuration
@ConditionalOnClass(TrackerClient.class)
@ConditionalOnProperty(name = "dfs.dfs-type", havingValue = "FastDFS")
@EnableConfigurationProperties(FastDFSProperties.class)
public class FastDFSAutoConfiguration {

    private final FastDFSProperties properties;

    public FastDFSAutoConfiguration(FastDFSProperties properties) {
        this.properties = properties;
    }

    @Bean
    public ConnectionPool pool() {
        return new ConnectionPool(properties);
    }

    @Bean
    public FastDFSClient fastDFSClient(ConnectionPool pool) {
        return new FastDFSClient(pool);
    }

}
