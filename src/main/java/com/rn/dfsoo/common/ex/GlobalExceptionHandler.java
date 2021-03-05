package com.rn.dfsoo.common.ex;

import com.rn.dfsoo.common.config.AppConfigProperties;
import com.rn.dfsoo.common.model.ExRet;
import com.rn.dfsoo.common.model.GlobalRet;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.util.Date;

/**
 * Description：全局统一异常处理器
 *
 * @author 然诺
 * @date 2019/8/28
 */
@Slf4j
@RestControllerAdvice
@ConditionalOnProperty(prefix = "spring.application", name = "ignore-global", havingValue = "false", matchIfMissing = true)
public class GlobalExceptionHandler {

	@Resource
	private AppConfigProperties appConfigProperties;

	/**
	 * 业务运行时异常
	 */
	@ExceptionHandler(BizException.class)
	public GlobalRet handleBizException(BizException ex) {
		log.error("BizException异常：[{}]", ex.getMessage());
		if (ex.getErrorCode() == null) {
			return buildRet(appConfigProperties, ex);
		}
		return new GlobalRet<>(ex.getErrorCode());
	}

	/**
	 * Servlet 容器相关异常处理
	 */
	@ExceptionHandler(ServletException.class)
	public GlobalRet handleServletException(ServletException ex) {
		HttpStatus status;
		if (ex instanceof HttpRequestMethodNotSupportedException) {
			status = HttpStatus.METHOD_NOT_ALLOWED;
		} else if (ex instanceof HttpMediaTypeNotSupportedException) {
			status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
		} else if (ex instanceof HttpMediaTypeNotAcceptableException) {
			status = HttpStatus.NOT_ACCEPTABLE;
		} else if (ex instanceof MissingPathVariableException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof MissingServletRequestParameterException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof ServletRequestBindingException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof MissingServletRequestPartException) {
			status = HttpStatus.BAD_REQUEST;
		} else if (ex instanceof NoHandlerFoundException) {
			status = HttpStatus.NOT_FOUND;
		} else {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		log.error("Servlet容器异常 ex={}:", ex.getMessage(), ex);
		return buildRet(appConfigProperties, ex, status.value());
	}

	/**
	 * 请求参数绑定异常
	 */
	@ExceptionHandler(BindException.class)
	public GlobalRet bindExceptionHandler(BindException ex) {
		GlobalRet ret = new GlobalRet<>(ex);
		ret.setError(buildError(appConfigProperties, ex));
		FieldError fieldError = ex.getBindingResult().getFieldError();
		assert fieldError != null;
		ret.setMessage(fieldError.getDefaultMessage());
		log.error("请求参数绑定异常:{}(请检查参数：[{}])", fieldError.getDefaultMessage(), fieldError.getField(), ex);
		return ret;
	}

	/**
	 * 请求参数验证异常
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public GlobalRet methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
		GlobalRet ret = new GlobalRet<>(ex);
		ret.setError(buildError(appConfigProperties, ex));
		FieldError fieldError = ex.getBindingResult().getFieldError();
		String errorMsg;
		if (fieldError != null) {
			log.error(String.format("%s (请检查参数：[%s])", fieldError.getDefaultMessage(), fieldError.getField()), ex);
			errorMsg = fieldError.getDefaultMessage();
		} else {
			errorMsg = "请求参数验证异常";
			log.error(errorMsg, ex);
		}
		ret.setMessage(errorMsg);
		return ret;
	}

//	/**
//	 * 数据完整性违规异常
//	 */
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public GlobalRet dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
//		GlobalRet ret = new GlobalRet<>(GlobalExCode.DB_DATA_INTEGRITY_VIOLATION_EX);
//		ret.setError(buildError(appConfigProperties, ex));
//		log.error("数据完整性违规异常：{}", ex.getMessage(), ex);
//		return ret;
//	}

	/**
	 * 其他任何异常返回服务器内部错误
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public GlobalRet exceptionHandler(Exception ex) {
		log.error("全局异常信息：{}", ex.getMessage(), ex);
		return buildRet(appConfigProperties, ex);
	}

	/**
	 * 构造错误响应对象
	 *
	 * @param appConfigProperties 系统配置
	 * @param th                  异常
	 * @return 错误响应对象
	 */
	private static GlobalRet buildRet(AppConfigProperties appConfigProperties, Throwable th) {
		GlobalRet ret = new GlobalRet<>(th);
		ret.setError(buildError(appConfigProperties, th));
		return ret;
	}

	/**
	 * 构造指定响应码的错误响应对象
	 *
	 * @param appConfigProperties 系统配置
	 * @param th                  异常
	 * @param status              状态码
	 * @return 错误响应对象
	 */
	private static GlobalRet buildRet(AppConfigProperties appConfigProperties, Throwable th, int status) {
		GlobalRet ret = new GlobalRet<>(th);
		ret.setCode(status);
		ret.setError(buildError(appConfigProperties, th));
		return ret;
	}

	/**
	 * 构造异常信息对象
	 *
	 * @param appConfigProperties 系统配置
	 * @param th                  异常
	 * @return 错误响应对象
	 */
	static ExRet buildError(AppConfigProperties appConfigProperties, Throwable th) {
		if (!appConfigProperties.isOutputException()) {
			return null;
		} else {
			ExRet exRet = new ExRet();
			exRet.setDate(new Date());
			exRet.setType(th.getClass().getName());
			exRet.setMessage(ExceptionUtils.getMessage(th));
			if (appConfigProperties.isOutputExceptionStackTrace()) {
				exRet.setStackTrace(ExceptionUtils.getStackTrace(th));
			}
			return exRet;
		}
	}

}
