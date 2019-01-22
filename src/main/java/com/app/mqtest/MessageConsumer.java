package com.app.mqtest;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;

@Component
public class MessageConsumer {
	//@RabbitListener(queues = QueueConstants.MESSAGE_QUEUE_NAME_CSGJ)
	public void processMessage(Channel channel,String message,Message mqmessage) {
		try { // 模拟消息处理失败
			//int a = 3 / 0;
			// false只确认当前一个消息收到，true确认所有consumer获得的消息
			// channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			//System.out.println(111);
		} catch (Exception e) {

/*			if (message.getMessageProperties().getRedelivered()) {
				System.out.println("消息已重复处理失败,拒绝再次接收...");
				try {
					channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);// requeue为false,拒绝
				} catch (IOException e1) {
				}
			} else {*/
/*				System.out.println("消息即将再次返回队列处理...");
				try {
					channel.basicReject(message.getMessageProperties().getDeliveryTag(), true); // requeue为true重新回到队列
				} catch (IOException e1) {
				}*/
			/*}*/
	//	e.printStackTrace();
		System.out.println(222);

		}
		System.out.println(9999);
	}
}