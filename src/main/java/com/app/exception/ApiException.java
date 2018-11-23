package com.app.exception;

import com.app.packet.response.ApiResponseCode;

public class ApiException extends RuntimeException {

	private int errorCode = ApiResponseCode.ERROR_SERVER_FAILURE;

	public ApiException() {
		super();

	}

	public ApiException(String message) {
		super(message);
	}

	public ApiException(String message, int errorCode) {
		super(message);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
