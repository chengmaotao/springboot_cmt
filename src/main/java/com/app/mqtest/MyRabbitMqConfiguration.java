package com.app.mqtest;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyRabbitMqConfiguration {
	/** * 交换配置 * * @return */
	@Bean
	public DirectExchange messageDirectExchange_csgj() {
		return (DirectExchange) ExchangeBuilder.directExchange(QueueConstants.MESSAGE_EXCHANGE_CSGJ).durable(true).build();
	}

	/** * 消息队列声明 * * @return */
	@Bean
	public Queue messageQueue_csgj() {
		return QueueBuilder.durable(QueueConstants.MESSAGE_QUEUE_NAME_CSGJ).build();
	}

	/** * 消息绑定 * * @return */
	@Bean
	public Binding messageBinding_csgj() {
		return BindingBuilder.bind(messageQueue_csgj()).to(messageDirectExchange_csgj()).with(QueueConstants.MESSAGE_ROUTE_KEY_CSGJ);
	}
	
	
	@Bean
	public DirectExchange messageDirectExchange_flb() {
		return (DirectExchange) ExchangeBuilder.directExchange(QueueConstants.MESSAGE_EXCHANGE_FLB).durable(true).build();
	}

	/** * 消息队列声明 * * @return */
	@Bean
	public Queue messageQueue_flb() {
		return QueueBuilder.durable(QueueConstants.MESSAGE_QUEUE_NAME_FLB).build();
	}

	/** * 消息绑定 * * @return */
	@Bean
	public Binding messageBinding_flb() {
		return BindingBuilder.bind(messageQueue_flb()).to(messageDirectExchange_flb()).with(QueueConstants.MESSAGE_ROUTE_KEY_FLB);
	}
	
}
