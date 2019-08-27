package com.hyc.userabbitmq.mq.producer;

import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.mapper.OrderMessageMapper;
import com.hyc.userabbitmq.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderMessageSender {

    public static final Logger logger = LoggerFactory.getLogger(OrderMessageSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private OrderMessageMapper orderMessageMapper;

    private RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            String messageId = correlationData.getId();
            if (ack) {
                // confirm成功，表示消息已经发送到exchange，但是不代表消费端已经接收。
                orderMessageMapper.updateSendStatus(messageId, MessageStatusEnum.SUCCESS, new Date());
            }
            else {
                // confirm失败，消息还未发送到exchange
                logger.error("messageId:{} confirm fail", messageId);
            }
        }
    };

    private RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            // return 失败，表示消息还未发送到队列,再次将状态变更为待发送
            logger.error("message return fail, message:{}, replyCode:{}, replyText:{}, exchange:{}, routingKey:{}", message, routingKey, replyText, exchange, routingKey);
            String messageId = message.getMessageProperties().getCorrelationId();
            orderMessageMapper.updateSendStatus(messageId, MessageStatusEnum.SENDING, new Date());
        }
    };

    public void sendMessage(Order order) {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("exchange-order", "order", order, correlationData);
    }
}
