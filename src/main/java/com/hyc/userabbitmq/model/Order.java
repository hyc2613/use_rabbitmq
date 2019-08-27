package com.hyc.userabbitmq.model;

import lombok.Data;

@Data
public class Order {

    private Long id;
    private String name;
    private String messageId;

}
