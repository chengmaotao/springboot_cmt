package com.app.exception;

import com.app.packet.response.ApiResponseCode;

public class ParamValidationException extends ApiException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParamValidationException(String message) {
		super(message, ApiResponseCode.ERROR_PARAM);
	}

}
