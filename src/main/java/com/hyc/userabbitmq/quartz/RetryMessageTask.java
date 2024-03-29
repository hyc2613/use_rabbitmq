package com.hyc.userabbitmq.quartz;

import com.alibaba.fastjson.JSON;
import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.mapper.OrderMessageMapper;
import com.hyc.userabbitmq.model.Order;
import com.hyc.userabbitmq.model.OrderMessage;
import com.hyc.userabbitmq.mq.producer.OrderMessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public class RetryMessageTask {

    public static final Logger logger = LoggerFactory.getLogger(RetryMessageTask.class);
    @Autowired
    private OrderMessageMapper orderMessageMapper;
    @Autowired
    private OrderMessageSender orderMessageSender;

    /**
     * initialDelay 表示第一次执行任务前停顿xx毫秒
     * fixedDelay 表示每隔xx毫秒执行一次
     */
    @Scheduled(initialDelay = 5000, fixedDelay = 10000)
    public void retrySendMessage() {
        List<OrderMessage> orderMessageList = orderMessageMapper.listSendingMessage();
        orderMessageList.forEach(orderMessage -> {
            if (orderMessage.getRetryTimes() >= 3) {
                // 重试次数达到阀值，后续不再往队列发送
                orderMessageMapper.updateSendStatus(orderMessage.getId(), MessageStatusEnum.FAIL, new Date());
            }
            else {
                // 再次尝试发送，将重试次数+1

                orderMessageMapper.update4ReSend(orderMessage.getId(), new Date());
                Order order = JSON.parseObject(orderMessage.getMessage(), Order.class);
                orderMessageSender.sendMessage(order);
            }
        });
    }


}
