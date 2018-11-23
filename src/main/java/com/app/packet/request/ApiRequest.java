package com.app.packet.request;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.exception.ParamValidationException;
import com.app.util.LocaleMessageUtil;

public class ApiRequest {

	private static Logger logger = LoggerFactory.getLogger(ApiRequest.class);

	private final static String ACCTION_CODE = "actionCode"; // 方法
	private final static String PACKET = "packet"; // 方法参数 json 字符串
	private static final String LANGUAGE = "language";

	private String actionCode;
	private String packet;
	private String language;

	private LocaleMessageUtil localMessage;

	public ApiRequest(String actionCode, String packet, String language) {
		this.actionCode = actionCode;
		this.packet = packet;
		this.language = language;
	}

	public static ApiRequest generate(HttpServletRequest request, LocaleMessageUtil localMessage) {
		String actionCode = request.getParameter(ACCTION_CODE);
		String packet = request.getParameter(PACKET);
		String language = request.getParameter(LANGUAGE);

		ApiRequest apiRequest = new ApiRequest(actionCode, packet, language);
		apiRequest.setLocalMessage(localMessage);
		return apiRequest;
	}

	public void validate() {
		if (actionCode == null) {
			logger.error("actionCode is null");
			throw new ParamValidationException(localMessage.getMessage("param.error.actionCode", getLanguage()));
		}
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getPacket() {
		return packet;
	}

	public void setPacket(String packet) {
		this.packet = packet;
	}

	public LocaleMessageUtil getLocalMessage() {
		return localMessage;
	}

	public void setLocalMessage(LocaleMessageUtil localMessage) {
		this.localMessage = localMessage;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	

}
