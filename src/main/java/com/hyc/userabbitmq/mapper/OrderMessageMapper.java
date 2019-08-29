package com.hyc.userabbitmq.mapper;

import com.hyc.userabbitmq.enums.MessageStatusEnum;
import com.hyc.userabbitmq.model.OrderMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import sun.plugin2.message.Message;

import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMessageMapper {

    void insertOrder(OrderMessage orderMessage);

    void updateSendStatus(@Param("id") String messageId, @Param("status") MessageStatusEnum messageStatusEnum, Date updateTime);

    void update4ReSend(@Param("id") String messageId, Date updateTime);

    List<OrderMessage> listSendingMessage();
}
