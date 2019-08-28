package com.hyc.userabbitmq.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列的配置信息
 */
@Configuration
public class OrderMQ {

    public static final String ORDER_EXCHANGE_NAME = "order.exchange";
    public static final String ORDER_QUEUE_NAME = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.#";

    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE_NAME);
    }

    @Bean
    public Exchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANGE_NAME);
    }

    @Bean
    public Binding orderBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with(ORDER_ROUTING_KEY).noargs();
    }
}
