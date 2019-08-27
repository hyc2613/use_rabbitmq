package com.hyc.userabbitmq.mapper;

import com.hyc.userabbitmq.model.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {

    @Insert("insert into order_info(id, name, message_id) values(#{id}, #{name}, #{messageId})")
    void insert(Order order);
}
