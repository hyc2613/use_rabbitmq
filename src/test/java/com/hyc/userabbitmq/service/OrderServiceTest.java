package com.hyc.userabbitmq.service;

import com.hyc.userabbitmq.model.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void save() {
        Order order = new Order();
        order.setId(1);
        order.setMessageId(UUID.randomUUID().toString());
        order.setName("订单1");
        orderService.save(order);
    }
}