package com.hyc.userabbitmq.controller;

import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.mq.config.OrderMQ;
import com.hyc.userabbitmq.mq.producer.OrderMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Random;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {
    private Order generateOrder() {
        Order order = new Order();
        order.setId(new Random().nextLong());
        order.setName("orderName:"+order.getId());
        order.setMessageId(UUID.randomUUID().toString());
        return order;
    }

    @Autowired
    private OrderMessageSender orderMessageSender;

    @GetMapping("/normal")
    @ResponseBody
    public void messageReceived() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order);
    }

    @GetMapping("/exError")
    @ResponseBody
    public void exchangeError() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order, "order.ex1", "order."+order.getMessageId());
    }

    @GetMapping("/routingError")
    @ResponseBody
    public void routingKeyError() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order, OrderMQ.ORDER_EXCHANGE_NAME, "AAAA.order.");
    }
}
