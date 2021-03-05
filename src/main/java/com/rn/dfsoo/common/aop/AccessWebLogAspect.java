package com.rn.dfsoo.common.aop;

import com.rn.dfsoo.common.constant.GlobalConstants;
import com.rn.dfsoo.common.model.GlobalRet;
import com.rn.dfsoo.common.mvc.ContextHandler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Description: 访问日志记录切面
 *
 * @author 然诺
 * @date 2020/5/14
 */
@Aspect
@Component
@Lazy(false)
public class AccessWebLogAspect {
	private static Logger log = LoggerFactory.getLogger(AccessWebLogAspect.class);

	private static final String REQUEST_TIME = "rt";
	private static final String REQUEST_INFO = "ri";

	@Before("accessWebLog()")
	public void doBefore(JoinPoint joinPoint) {
		// 开始时间
		ContextHandler.set(REQUEST_TIME, System.currentTimeMillis());
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		assert attributes != null;
		HttpServletRequest request = attributes.getRequest();
		// 请求地址、请求方法
		String requestInfo = request.getRequestURL() + GlobalConstants.LOG_SEP + request.getMethod();
		ContextHandler.set(REQUEST_INFO, requestInfo);
	}

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
	public void accessWebLog() {
		// Only as an entry point
	}

	@AfterReturning(pointcut = "accessWebLog()", returning = "ret")
	public void doAfterReturning(JoinPoint joinPoint, Object ret) {
		// 记录访问日志
		String resultCode = null;
		if (ret instanceof GlobalRet) {
			GlobalRet globalRet = (GlobalRet) ret;
			resultCode = String.valueOf(globalRet.getCode());
		}
		long castTime = System.currentTimeMillis() - (long) ContextHandler.get(REQUEST_TIME);
		// 日志格式：耗时 请求地址 请求方式 响应码
		log.info("{}ms\t{}\t{}", castTime, ContextHandler.get(REQUEST_INFO), resultCode);
	}

	@AfterThrowing(pointcut = "accessWebLog()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
       // Do nothing
	}

}
