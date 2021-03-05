package com.rn.dfsoo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description: 区块链设备管理平台服务
 *
 * @author 然诺
 * @date 2020/5/7
 */
@MapperScan(basePackages = "com.rn.dfsoo.*.mapper")
@SpringBootApplication
public class DFSApplication {
	public static void main(String[] args) {
		SpringApplication.run(DFSApplication.class, args);
	}
}
