package com.hyc.userabbitmq.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息队列的配置信息
 */
@Configuration
public class OrderMQ {

    public static final String ORDER_EXCHANGE_NAME = "order.exchange";
    public static final String ORDER_QUEUE_NAME = "order.queue";
    public static final String ORDER_ROUTING_KEY = "order.#";

    // 定义死信队列的相关KEY值
    public static final String DEAD_QUEUE_NAME = "dead.queue";
    public static final String DEAD_EXCHANGE_NAME = "dead.exchange";
    public static final String DEAD_ROUTING_KEY = "dead";
    public static final String DEAD_LETTER_EXCHANGE_KEY = "x-dead-letter-exchange";
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    @Bean
    public Queue deadQueue() {
        return new Queue(DEAD_QUEUE_NAME);
    }

    @Bean
    public Exchange deadExchange() {
        return new DirectExchange(DEAD_EXCHANGE_NAME);
    }

    @Bean
    public Binding deadBinding() {
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with(DEAD_ROUTING_KEY).noargs();
    }

    @Bean
    public Queue orderQueue() {
        // 给order.queue 设置私信队列，如果消息没有被ack，则进入死信队列
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(DEAD_LETTER_EXCHANGE_KEY, DEAD_EXCHANGE_NAME);
        arguments.put(DEAD_LETTER_ROUTING_KEY, DEAD_ROUTING_KEY);
        return new Queue(ORDER_QUEUE_NAME, true, false, false, arguments);
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
