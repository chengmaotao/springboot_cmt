package com.app.mq;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class RabbitMqListener {

	private static Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

	//@RabbitListener(queues = "bobo") // 监听器监听指定的Queue
	public void processC(JSONObject str) {
		logger.info("监听：" + str.getString("name"));
		logger.info("监听：" + str.getDate("date"));
	}
	
	
	//@RabbitListener(queues = "xiaoqian") // 监听器监听指定的Queue
	public void process2C(byte[] body) throws UnsupportedEncodingException {
		String message = new String(body, "UTF-8");
		
		logger.info("监听liwei：" + message);
		
	}
}
