package com.hyc.userabbitmq.mq.listener;

import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.mq.config.OrderMQ;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;

//
@Component
@RabbitListener(queues = OrderMQ.DEAD_QUEUE_NAME)
public class DeadLetterListener {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);


    @RabbitHandler(isDefault = true)
    public void dealDeadLetter(Channel channel, Message message, @Payload Order order) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        Order order1 = (Order) SerializationUtils.deserialize(message.getBody());
        logger.info("dead letter, orderId:{}", order.getId());
        logger.info("deliveryTag:{}, messageBody:{}", deliveryTag, order1);
        channel.basicAck(deliveryTag, false);
    }

}
