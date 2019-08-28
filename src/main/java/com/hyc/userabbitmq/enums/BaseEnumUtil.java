package com.hyc.userabbitmq.enums;

public class BaseEnumUtil {

    private BaseEnumUtil() {}

    public static <E extends Enum<?> & BaseEnum> E codeOf(Class<E> enumClass, int code) {
        E[] enumConstants = enumClass.getEnumConstants();
        for (E e : enumConstants) {
            if (e.getValue() == code)
                return e;
        }
        return null;
    }
}



