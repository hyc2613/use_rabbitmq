package com.hyc.userabbitmq.model;

import com.hyc.userabbitmq.enums.MessageStatusEnum;
import lombok.Data;
import org.springframework.util.Assert;

import java.util.Date;

@Data
public class OrderMessage {

    private String id;
    private String message;
    private Integer retryTimes;
    private Date createTime;
    private Date updateTime;
    private Integer status;
    private Date nextRetryTime;

    public void setStatusWithEnum(MessageStatusEnum messageStatusEnum) {
        Assert.notNull(messageStatusEnum, "枚举不能为null");
        setStatus(messageStatusEnum.getValue());
    }

}
