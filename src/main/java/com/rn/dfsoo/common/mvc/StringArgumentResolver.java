package com.rn.dfsoo.common.mvc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

/**
 * Description: 字符串参数转换器
 *
 * @author 然诺
 * @date 2019/11/19
 */
public class StringArgumentResolver extends AbstractNamedValueMethodArgumentResolver {
	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter methodParameter) {
		return new NamedValueInfo("lastOneHandler", false, ValueConstants.DEFAULT_NONE);
	}

	@Override
	protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
		String[] param = request.getParameterValues(name);
		if (param == null) {
			return null;
		}
		if (StringUtils.isBlank(param[0])) {
			return null;
		}
		return param[0];
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(String.class);
	}

}
