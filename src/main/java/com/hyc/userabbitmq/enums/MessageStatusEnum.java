package com.hyc.userabbitmq.enums;

public enum MessageStatusEnum implements BaseEnum {
    /**
     * 发送中
     */
    SENDING(0),
    /**
     * 发送成功
     */
    SUCCESS(1),
    /**
     * 发送失败
     */
    FAIL(2);


    private final Integer value;

    MessageStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
