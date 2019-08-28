package com.hyc.userabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.mapper.OrderMapper;
import com.hyc.userabbitmq.mapper.OrderMessageMapper;
import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.model.OrderMessage;
import com.hyc.userabbitmq.mq.producer.OrderMessageSender;
import com.hyc.userabbitmq.mq.producer.OrderSender;
import com.hyc.userabbitmq.util.DateUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderMessageMapper orderMessageMapper;

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Transactional(rollbackFor = Exception.class)
    public void save(Order order) {
        orderMapper.insert(order);
        Date now = new Date();
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setId(order.getMessageId());
        orderMessage.setMessage(JSON.toJSONString(order));
        orderMessage.setCreateTime(now);
        orderMessage.setUpdateTime(now);
        orderMessage.setNextRetryTime(DateUtil.nextSendMessageTime());
        orderMessage.setStatus(MessageStatusEnum.SENDING);
        orderMessage.setRetryTimes(0);
        orderMessageMapper.insertOrder(orderMessage);
        // 往消息队列发送一条消息
        orderMessageSender.sendMessage(order);
    }

}
