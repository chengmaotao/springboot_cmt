package com.app.packet.response;

import lombok.Data;

@Data
@lombok.ToString
public class ApiResponse {
	private int code = ApiResponseCode.SUCCESS;
	private String errorMessage = "";
	private String responsePacket = "";
}
