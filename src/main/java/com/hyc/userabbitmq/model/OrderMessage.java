package com.hyc.userabbitmq.model;

import lombok.Data;

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

}
