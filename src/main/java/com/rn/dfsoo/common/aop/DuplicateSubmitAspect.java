package com.rn.dfsoo.common.aop;

import com.rn.dfsoo.common.annotation.DuplicateSubmitToken;
import com.rn.dfsoo.common.ex.DuplicateSubmitException;
import com.rn.dfsoo.common.ex.GlobalExCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Description: 防止表单重复提交切面
 *
 * @author 然诺
 * @date 2019/10/17
 */
@Slf4j
@Aspect
@Component
public class DuplicateSubmitAspect {

	private static final String DUPLICATE_TOKEN_PREFIX = "duplicate_token_key";

	@Before("duplicateSubmit() && @annotation(token)")
	public void before(final JoinPoint joinPoint, DuplicateSubmitToken token) throws DuplicateSubmitException {
		if (token != null) {
			HttpServletRequest request = getRequest();
			boolean isSave = token.save();
			if (isSave) {
				String key = getDuplicateTokenKey(joinPoint);
				Object obj = request.getSession().getAttribute(key);
				if (null == obj) {
					String uuid = UUID.randomUUID().toString();
					request.getSession().setAttribute(key, uuid);
				} else {
					throw new DuplicateSubmitException(GlobalExCode.REQUEST_REPEAT);
				}
			}
		}
	}

	@Pointcut("@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.web.bind.annotation.RestControllerAdvice)")
	public void duplicateSubmit() {
		// only as Pointcut
	}

	@AfterReturning("duplicateSubmit() && @annotation(token)")
	public void doAfterReturning(JoinPoint joinPoint, DuplicateSubmitToken token) {
		// 处理完请求，返回内容
		if (null != token) {
			HttpServletRequest request = getRequest();
			boolean isSave = token.save();
			if (isSave) {
				String key = getDuplicateTokenKey(joinPoint);
				Object obj = request.getSession().getAttribute(key);
				if (null != obj && token.type() == DuplicateSubmitToken.REQUEST) {
					request.getSession(false).removeAttribute(key);
				}
			}
		}
	}

	/**
	 * 异常后处理
	 */
	@AfterThrowing(pointcut = "duplicateSubmit()&& @annotation(token)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e, DuplicateSubmitToken token) {
		if (null != token && !(e instanceof DuplicateSubmitException)) {
			// 处理重复提交本身之外的异常
			HttpServletRequest request = getRequest();
			boolean isSave = token.save();
			if (isSave) {
				String key = getDuplicateTokenKey(joinPoint);
				Object obj = request.getSession().getAttribute(key);
				if (null != obj) {
					// 方法执行完毕移除请求重复标记
					request.getSession(false).removeAttribute(key);
				}
			}
		}
	}

	/**
	 * 获取重复提交key
	 */
	private String getDuplicateTokenKey(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		return DUPLICATE_TOKEN_PREFIX + "," + methodName;
	}

	/**
	 * 获取请求对象
	 */
	private HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		Assert.notNull(attributes, "Servlet request attribute is null");
		return attributes.getRequest();
	}

}