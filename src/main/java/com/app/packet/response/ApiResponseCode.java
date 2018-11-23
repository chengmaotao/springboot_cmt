package com.app.packet.response;

public abstract class ApiResponseCode {
	public static final int SUCCESS = 0;

	public static final int ERROR_PARAM = 5; // 参数错误 code

	public static final int ERROR_SERVER_FAILURE = 999; // 服务错误
}
