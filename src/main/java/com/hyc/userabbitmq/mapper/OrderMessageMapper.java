package com.hyc.userabbitmq.mapper;

import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.model.OrderMessage;
import org.apache.ibatis.annotations.Mapper;
import sun.plugin2.message.Message;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMessageMapper {

    void insert(OrderMessage orderMessage);

    void updateSendStatus(String messageId, MessageStatusEnum messageStatusEnum, Date updateTime);

    void update4ReSend(String messageId, Date updateTime);

    List<OrderMessage> listSendingMessage();
}
