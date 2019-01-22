package com.app.qywx;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.app.util.HttpUtils;

public class WXUtils {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	private static final String corpid = "wwa9ae82c6c0be5f6a"; // 企业id
	private static final String corpsecret = "pD6aVuqEOO_sEDOAynvbZSHUInaBo2gQkkQFgWldDY8";// 应用凭证密钥

	private static final String encoding = "UTF-8";

	public static String getAccessToken() {
		String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + corpid + "&corpsecret=" + corpsecret;
		try {
			return HttpUtils.sendGetRequest(url, encoding);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getStackTrace(e));
			return "";
		}
		
		// l36DzSeDwFf2c2e1BmiL81HVyob9t_ZEyRnWpzbizyoZVUXr8Mvy2Oo4pn8H1X4Ji1hitZErbnYe50LbFLeUKehlIYTLl5SIrOhez5j4c3JVDBVn1pptaOGkFa28DebtbxrwO74YkluRRuj54mf7a72jfgxXrb4uR_gS7RaGhApA4E5RxQSNOI5n8QP6DwK4ZrhTlMGD-qymEcMNV1SfLQ
	}

	public static void main(String[] args) {
		System.out.println(getAccessToken());
	}
}
