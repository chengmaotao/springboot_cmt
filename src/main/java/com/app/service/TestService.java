package com.app.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.app.dao.CustomerMapper;
import com.app.entity.Customer;
import com.app.exception.ApiException;
import com.app.packet.request.ApiRequest;
import com.app.packet.request.Test01Request;
import com.app.packet.response.ApiResponse;
import com.app.util.LocaleMessageUtil;

@Service
public class TestService {

	@Autowired
	private LocaleMessageUtil localMessage;

	@Autowired
	private CustomerMapper customerMapper;

	private static Logger logger = LoggerFactory.getLogger(TestService.class);

	public ApiResponse test01(ApiRequest apiRequest) throws ApiException {
		Test01Request packet = JSON.parseObject(apiRequest.getPacket(), Test01Request.class);

		logger.info("test01 参数：" + packet);

		if (StringUtils.equals(packet.getMobile(), "15664008013")) {
			logger.warn("进入警告了啊");
			throw new ApiException("业务处问题了啊", 333);
		} else if (StringUtils.equals(packet.getMobile(), "18539869852")) {
			int num = 0;
			int t = 3 / num;
			return null;
		} else {
			
			Customer selectById = customerMapper.selectById(230707);
			
			logger.info("Customer  selectById: " + selectById);
			
			Customer selectByUsername = customerMapper.selectByUsername("13146603515");
			
			logger.info("Customer  selectByUsername: " + selectByUsername);
			
			Customer selectByShortUrl = customerMapper.selectByShortUrl("0c989b81b2");
			logger.info("Customer  selectByShortUrl: " + selectByShortUrl);
			
			JSONObject data = new JSONObject();
			data.put("name", "豆海霞");
			data.put("日期", "2018-11-21");

			return genResponse(data);
		}

	}

	public ApiResponse genResponse(JSONObject packet) {

		ApiResponse apiResponse = new ApiResponse();
		String resPacketJSON = JSON.toJSONString(packet);
		apiResponse.setResponsePacket(resPacketJSON);

		return apiResponse;
	}
}
