package com.app.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.constant.ApiRequestAction;
import com.app.exception.ApiException;
import com.app.exception.ParamValidationException;
import com.app.packet.request.ApiRequest;
import com.app.packet.response.ApiResponse;
import com.app.packet.response.ApiResponseCode;
import com.app.service.ApiService;
import com.app.service.TestService;
import com.app.util.LocaleMessageUtil;

@RestController
public class ApplicationController {

	@Autowired
	private LocaleMessageUtil localMessage;

	private static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private ApiService apiService;
	@Autowired
	private TestService testService;

	@PostMapping("/1.0/endpoint")
	public ApiResponse handleApiRequest(HttpServletRequest request, @RequestBody String str) throws Exception {
		apiService.requestInfo(request);
		logger.info("@RequestBody: " + str);
		logger.info("requestURL: " + apiService.getRequestURL());
		logger.info("requestParameters: " + apiService.getRequestParameters());
		logger.info("requestIp: " + apiService.getRequestIp());

		ApiRequest apiRequest = ApiRequest.generate(request, localMessage);

		apiRequest.validate();

		ApiResponse apiResponse;
		try {
			switch (Integer.parseInt(apiRequest.getActionCode())) {
			case ApiRequestAction.REGISTER_AND_LOGIN_VERIFY: {
				apiResponse = testService.test01(apiRequest);
				break;
			}
			case ApiRequestAction.REGISTER_AND_LOGIN: {

			}
			default: {
				throw new ParamValidationException(localMessage.getMessage("param.error.actionCode", apiRequest.getLanguage()));

			}

			}
		} catch (NumberFormatException e) {
			throw new ParamValidationException(localMessage.getMessage("param.error.actionCode", apiRequest.getLanguage()));
		}

		logger.info("接口响应:" + apiResponse);
		return apiResponse;

	}

	@ExceptionHandler(ApiException.class)
	public ApiResponse handleApiException(final ApiException exception) {
		// logger.error(ExceptionUtils.getFullStackTrace(exception));
		logger.error(ExceptionUtils.getStackTrace(exception));
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(exception.getErrorCode());
		apiResponse.setErrorMessage(exception.getMessage());

		return apiResponse;
	}

	@ExceptionHandler(Throwable.class)
	protected ApiResponse handleApplicationException(final Throwable throwable) {

		logger.error(ExceptionUtils.getStackTrace(throwable));
		throwable.printStackTrace();
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setCode(ApiResponseCode.ERROR_SERVER_FAILURE);
		apiResponse.setErrorMessage("未知的服务错误");

		return apiResponse;
	}
}
