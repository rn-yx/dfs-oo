package com.rn.dfsoo.common.mvc;

import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Description: 请求包装（备份流）
 *
 * @author 然诺
 * @date 2019/11/27
 */
public class RequestWrapper extends HttpServletRequestWrapper {
	/**
	 * 保存流的空间
	 */
	private byte[] requestBody;

	public RequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		this.requestBody = StreamUtils.copyToByteArray(request.getInputStream());
	}

	@Override
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
	}

	@Override
	public ServletInputStream getInputStream() {
		if (requestBody == null) {
			requestBody = new byte[0];
		}
		final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				// do nothing
			}

			@Override
			public int read() throws IOException {
				return bais.read();
			}
		};
	}
}
