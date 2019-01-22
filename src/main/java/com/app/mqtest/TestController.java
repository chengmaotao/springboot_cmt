package com.app.mqtest;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

@RestController
public class TestController {
	@Autowired
	private MessageProducer messageProducer;

	@RequestMapping(value = "/index")
	public String index(@RequestBody String str) {
		// 将实体实例写入消息队列
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", "100");
		jsonObject.put("order", "order201812242222");
		jsonObject.put("mobile", "15664008013");
		jsonObject.put("accountAddress", "CTj37NcVWPb4ToknGnLYCBtqVEWQ2tHiKm5a6b6f1f55284ac195e11a23026b60e7");
		jsonObject.put("country", "86");
		jsonObject.put("amount", new BigDecimal("360"));
		jsonObject.put("date", "19930230171636");
		
		 messageProducer.sendMessage(str);
		
		
			return "sucess";

	}
}
