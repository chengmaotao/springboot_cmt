package com.app.mqtest;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMessage(String str) {

		rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {

			System.out.println("1111");
			System.out.println("消息唯一标识" + correlationData);
			System.out.println("消息确认结果" + ack);
			System.out.println("失败原因" + cause);

		});

		rabbitTemplate.setReturnCallback((Message message, int replyCode, String replyText, String exchange, String routingKey) -> {
			System.out.println("222");
			System.out.println("消息主体message" + message);
			System.out.println("消息replyCode" + replyCode);
			System.out.println("消息replyText" + replyText);
			System.out.println("消息使用的交换器" + exchange);
			System.out.println("消息使用的路由键" + routingKey);
		});
		
		
		System.out.println("333");
		rabbitTemplate.convertAndSend(QueueConstants.MESSAGE_EXCHANGE_CSGJ, QueueConstants.MESSAGE_ROUTE_KEY_CSGJ, str);
		System.out.println("444");

	}
}
