package com.hyc.userabbitmq.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private DateUtil() {}

    public static Date nextSendMessageTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 1);
        return calendar.getTime();
    }

}
