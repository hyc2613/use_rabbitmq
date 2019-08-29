package com.hyc.userabbitmq.mq.listener;

import com.hyc.userabbitmq.mq.config.OrderMQ;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RabbitListener(queues = OrderMQ.DEAD_QUEUE_NAME)
@Component
public class DeadLetterListener {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterListener.class);


    @RabbitHandler
    public void dealDeadLetter(Channel channel, Message message) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String messageId = message.getMessageProperties().getMessageId();
        String body = new String(message.getBody(), "utf-8");
        logger.info("deliveryTag:{}, messageId:{}, messageBody:{}", deliveryTag, messageId, body);
        channel.basicAck(deliveryTag, false);
    }

}
