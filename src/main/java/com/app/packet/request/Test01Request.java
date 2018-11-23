package com.app.packet.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Test01Request {
	private String name;

	private String mobile;

	private void validate() {

	}
}
