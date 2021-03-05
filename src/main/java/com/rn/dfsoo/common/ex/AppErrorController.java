package com.rn.dfsoo.common.ex;

import com.rn.dfsoo.common.config.AppConfigProperties;
import com.rn.dfsoo.common.model.ExRet;
import com.rn.dfsoo.common.model.GlobalRet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Description：错误控制处理器
 *
 * @author rannuo
 * @date 2019/8/28
 */
@ApiIgnore
@Slf4j
@Controller
public class AppErrorController implements ErrorController {

	@Resource
	private AppConfigProperties appConfigProperties;

	/**
	 * 采用spring-boot 默认的映射/error
	 */
	private static final String DEFAULT_ERROR_VIEW = "/error";

	@Override
	public String getErrorPath() {
		return DEFAULT_ERROR_VIEW;
	}

	/**
	 * 异常错误处理
	 *
	 * @param request request
	 * @return GlobalRet
	 */
	@ResponseBody
	@RequestMapping(value = DEFAULT_ERROR_VIEW, produces = MediaType.APPLICATION_JSON_VALUE)
	public GlobalRet error(HttpServletRequest request) {
		// 获取HTTP状态
		HttpStatus httpStatus = getStatus(request);
		// 创建错误返回封装对象
		ExRet errorResult = new ExRet();
		// 获取异常信息
		Object object = request.getAttribute("javax.servlet.error.exception");
		if (object instanceof Exception) {
			Exception ex = (Exception) object;
			errorResult = GlobalExceptionHandler.buildError(appConfigProperties, ex);
			// 记录日志
			log.error("AppErrorController : reporting error ({}) | {}", httpStatus.value(), ex.getMessage());
		}
		if (errorResult != null) {
			errorResult.setDate(new Date());
		}

		// 构建返回结果
		GlobalRet ret = new GlobalRet<>();
		ret.setCode(httpStatus.value());
		ret.setMessage(httpStatus.getReasonPhrase());
		ret.setError(errorResult);
		return ret;
	}

	/**
	 * 未登录跳转数据返回接口
	 *
	 * @return 403 禁止访问
	 */
	@ApiIgnore
	@ResponseBody
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@RequestMapping("/forbidden")
	public GlobalRet forbidden(Integer errorCode) {
		if (errorCode == null) {
			return GlobalRet.failure(GlobalExCode.FORBIDDEN);
		}
		return GlobalRet.failure(GlobalExCode.fromStatus(errorCode));
	}

	/**
	 * 未授权跳转数据返回接口
	 *
	 * @return 401 没有访问权限
	 */
	@ApiIgnore
	@ResponseBody
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@RequestMapping("/unauthorized")
	public GlobalRet unauthorized(Integer errorCode) {
		if (errorCode == null) {
			return GlobalRet.failure(GlobalExCode.UNAUTHORIZED);
		}
		return GlobalRet.failure(GlobalExCode.fromStatus(errorCode));
	}

	/**
	 * 获取状态码
	 *
	 * @param request request
	 * @return HttpStatus
	 */
	private HttpStatus getStatus(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		if (statusCode == null) {
			return HttpStatus.INTERNAL_SERVER_ERROR;
		} else {
			try {
				return HttpStatus.valueOf(statusCode);
			} catch (Exception e) {
				return HttpStatus.INTERNAL_SERVER_ERROR;
			}
		}
	}

}

