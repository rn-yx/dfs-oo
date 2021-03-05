package com.rn.dfsoo.common.mvc;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Description: 流备份过滤器
 *
 * @author 然诺
 * @date 2019/11/27
 */
public class StreamBackupFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		RequestWrapper requestWrapper = null;
		if(request instanceof HttpServletRequest) {
			requestWrapper = new RequestWrapper((HttpServletRequest) request);
		}
		if(requestWrapper == null) {
			chain.doFilter(request, response);
		} else {
			chain.doFilter(requestWrapper, response);
		}
	}

	@Override
	public void destroy() {
		// do nothing
	}
}
