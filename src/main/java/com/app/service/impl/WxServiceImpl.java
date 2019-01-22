package com.app.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.app.qywx.WXUtils;
import com.app.service.RedisHandler;
import com.app.service.WxService;

@Component
public class WxServiceImpl implements WxService {

	private static final Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

	@Autowired
	private RedisHandler redisHandler;

	@Override
	public String getAccessToken() {
		String accessToken = redisHandler.getString("accessToken");

		if (StringUtils.isBlank(accessToken)) {
			accessToken = WXUtils.getAccessToken();

			if (StringUtils.isNotBlank(accessToken)) {

				JSONObject parseObject = JSONObject.parseObject(accessToken);

				Integer errcode = parseObject.getInteger("errcode");
				if (errcode != null && errcode == 0) {

					accessToken = parseObject.getString("access_token");
					redisHandler.putString("accessToken", accessToken, parseObject.getInteger("expires_in"));
				} else {
					logger.error("获取access_token 失败：" + accessToken);
				}

			}
		}
		return accessToken;
	}

}
