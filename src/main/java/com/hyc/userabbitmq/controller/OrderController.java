package com.hyc.userabbitmq.controller;

import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.mq.config.OrderMQ;
import com.hyc.userabbitmq.mq.producer.OrderMessageSender;
import com.hyc.userabbitmq.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/order")
public class OrderController {
    private Order generateOrder() {
        Order order = new Order();
        order.setId(new Random().nextInt());
        order.setName("orderName:"+order.getId());
        order.setMessageId(UUID.randomUUID().toString());
        return order;
    }

    @Autowired
    private OrderMessageSender orderMessageSender;

    @Autowired
    private OrderService orderService;

    @GetMapping("/save")
    public Object saveOrder() {
        Order order = generateOrder();
        orderService.save(order);
        return order;
    }

    @GetMapping("/normal")
    public void messageReceived() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order);
    }

    @GetMapping("/exError")
    public void exchangeError() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order, "order.ex1", "order."+order.getMessageId());
    }

    @GetMapping("/routingError")
    public void routingKeyError() {
        Order order = generateOrder();
        orderMessageSender.sendMessage(order, OrderMQ.ORDER_EXCHANGE_NAME, "AAAA.order.");
    }
}
