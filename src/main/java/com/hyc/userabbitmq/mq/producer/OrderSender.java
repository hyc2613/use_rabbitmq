package com.hyc.userabbitmq.mq.producer;

import com.alibaba.fastjson.JSON;
import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.mapper.OrderMapper;
import com.hyc.userabbitmq.mapper.OrderMessageMapper;
import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.model.OrderMessage;
import com.hyc.userabbitmq.util.DateUtil;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

public class OrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendOrderMessage(Order order) {
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("exchange-order", "order", order, correlationData);
    }

}
