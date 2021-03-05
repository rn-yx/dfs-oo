package com.rn.dfsoo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.function.Consumer;

/**
 * Description: 编程式的事务管理工具
 *
 * @author 然诺
 * @date 2019/10/11
 */
@Slf4j
@Component
public class TransactionUtils {

	@Autowired
	private PlatformTransactionManager transactionManager;

	/**
	 * 事务处理
	 *
	 * @param consumer 需要事务处理的方法
	 */
	@SuppressWarnings("unchecked")
	public void transact(Consumer consumer) {
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			consumer.accept(null);
			transactionManager.commit(status);
		} catch (Exception e) {
			transactionManager.rollback(status);
			log.error("执行事务处理失败", e);
		}
	}

}
