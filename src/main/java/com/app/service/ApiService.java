package com.app.service;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ApiService {
	private String requestURL;
	private String requestIp;
	private StringBuilder requestParameters;

	public void requestInfo(HttpServletRequest servletRequest) {
		if (servletRequest == null)
			return;

		StringBuffer requestURL = servletRequest.getRequestURL();
		String queryString = servletRequest.getQueryString();
		if (queryString != null) {
			requestURL.append("?").append(queryString);
		}
		this.requestURL = requestURL.toString();

		this.requestIp = getRequestIp(servletRequest);

		Enumeration<String> parameterNames = servletRequest.getParameterNames();
		requestParameters = new StringBuilder();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			requestParameters.append("&").append(parameterName).append("=").append(servletRequest.getParameter(parameterName));
		}
	}

	// 获取ip地址
	public String getRequestIp(HttpServletRequest servletRequest) {
		String ip = servletRequest.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = servletRequest.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return servletRequest.getRemoteAddr();
	}

	public String getRequestURL() {
		return requestURL;
	}

	public void setRequestURL(String requestURL) {
		this.requestURL = requestURL;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public StringBuilder getRequestParameters() {
		return requestParameters;
	}

	public void setRequestParameters(StringBuilder requestParameters) {
		this.requestParameters = requestParameters;
	}
}
