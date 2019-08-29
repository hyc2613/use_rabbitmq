package com.hyc.userabbitmq.mq.listener;

import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.mq.config.OrderMQ;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class OrderMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderMessageListener.class);

//    @RabbitListener(bindings = {@QueueBinding(
//            value = @Queue(value = "order", durable = "true"),
//            exchange = @Exchange(value = "exchange.order", type = "topic"),
//            key = "order.#"
//    )})
    @RabbitListener(queues = OrderMQ.ORDER_QUEUE_NAME)
    public void receiveOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        //消费者操作
        logger.info("---------收到消息，开始消费---------");
        logger.info("订单ID："+order.getId());

        /**
         * Delivery Tag 用来标识信道中投递的消息。RabbitMQ 推送消息给 Consumer 时，会附带一个 Delivery Tag，
         * 以便 Consumer 可以在消息确认时告诉 RabbitMQ 到底是哪条消息被确认了。
         * RabbitMQ 保证在每个信道中，每条消息的 Delivery Tag 从 1 开始递增。
         */
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        //ACK,确认一条消息已经被消费
        channel.basicAck(deliveryTag, false);
        // nack 或 reject并且requeue=false，则消息进入死信队列
//        channel.basicNack(deliveryTag, false, false);
//        channel.basicReject(deliveryTag, false);
    }

}
