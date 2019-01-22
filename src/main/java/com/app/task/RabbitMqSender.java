package com.app.task;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.app.util.DateUtils;

@Component
public class RabbitMqSender {
	private static Logger logger = LoggerFactory.getLogger(RabbitMqSender.class);
	@Autowired
	private AmqpTemplate template;

	//@Scheduled(fixedDelay = 60000)
	public void send() {
		logger.info("定时任务 开始……" + DateUtils.dateyyyyMMddHHmmssFormat(new Date()));
		JSONObject data = new JSONObject();
		data.put("name", "博博");
		data.put("date", new Date());
		template.convertAndSend("bobo", data);  // 笨方法 要首先创建好这个队列



		
		logger.info("定时任务 结束……" + DateUtils.dateyyyyMMddHHmmssFormat(new Date()));
	}
}
