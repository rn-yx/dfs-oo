package com.rn.dfsoo.utils;

import javax.validation.constraints.NotNull;

/**
 * Description: MIME 类型工具
 *
 * @author 然诺
 * @date 2020/7/16
 */
public class MIMEUtils {
	public static final String ALL_VALUE = "*/*";
	public static final String APPLICATION_ATOM_XML_VALUE = "application/atom+xml";
	public static final String APPLICATION_FORM_URLENCODED_VALUE = "application/x-www-form-urlencoded";
	public static final String APPLICATION_JSON_VALUE = "application/json";
	public static final String APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8";
	public static final String APPLICATION_OCTET_STREAM_VALUE = "application/octet-stream";
	public static final String APPLICATION_PDF_VALUE = "application/pdf";
	public static final String APPLICATION_PROBLEM_JSON_VALUE = "application/problem+json";
	public static final String APPLICATION_PROBLEM_JSON_UTF8_VALUE = "application/problem+json;charset=UTF-8";
	public static final String APPLICATION_PROBLEM_XML_VALUE = "application/problem+xml";
	public static final String APPLICATION_RSS_XML_VALUE = "application/rss+xml";
	public static final String APPLICATION_STREAM_JSON_VALUE = "application/stream+json";
	public static final String APPLICATION_XHTML_XML_VALUE = "application/xhtml+xml";
	public static final String APPLICATION_XML_VALUE = "application/xml";
	public static final String IMAGE_GIF_VALUE = "image/gif";
	public static final String IMAGE_JPEG_VALUE = "image/jpeg";
	public static final String IMAGE_PNG_VALUE = "image/png";
	public static final String MULTIPART_FORM_DATA_VALUE = "multipart/form-data";
	public static final String TEXT_EVENT_STREAM_VALUE = "text/event-stream";
	public static final String TEXT_HTML_VALUE = "text/html";
	public static final String TEXT_MARKDOWN_VALUE = "text/markdown";
	public static final String TEXT_PLAIN_VALUE = "text/plain";
	public static final String TEXT_XML_VALUE = "text/xml";

	/**
	 * 根据后缀获取对应的MEMI类型
	 *
	 * @param suffix
	 * @return
	 */
	public static String getContentTypeBySuffix(@NotNull String suffix) {
		switch (suffix) {
			case "gif":
				return IMAGE_GIF_VALUE;
			case "jpg":
				return IMAGE_JPEG_VALUE;
			case "png":
				return IMAGE_PNG_VALUE;
			case "html":
				return TEXT_HTML_VALUE;
			case "md":
				return TEXT_MARKDOWN_VALUE;
			case "txt":
				return TEXT_PLAIN_VALUE;
			case "xml":
				return TEXT_XML_VALUE;
			case "xhtml":
				return APPLICATION_XHTML_XML_VALUE;
			case "json":
				return APPLICATION_JSON_VALUE;
			case "pdf":
				return APPLICATION_PDF_VALUE;
			case "exe":
				return APPLICATION_OCTET_STREAM_VALUE;
			default:
				return ALL_VALUE;
		}
	}
}
