package com.hyc.userabbitmq.enums;

public class BaseEnumUtil {
    private BaseEnumUtil() {}

    /**
     * 根据value匹配相应的枚举实例
     * @param eClass
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends BaseEnum> E codeOf(Class<E> eClass, int value) {
        E[] enumConstants = eClass.getEnumConstants();
        for (E enumConstant : enumConstants) {
            if (enumConstant.getValue() == value) {
                return enumConstant;
            }
        }
        return null;
    }

}
