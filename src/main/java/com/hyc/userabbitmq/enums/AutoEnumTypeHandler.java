package com.hyc.userabbitmq.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 自动的枚举类型转换器
 * @param <E>
 */

public class AutoEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private BaseTypeHandler<E> defaultTypeHandler;

    public AutoEnumTypeHandler(Class<E> eClass) {
        if (eClass == null) {
            throw new IllegalArgumentException("枚举类型不能为null");
        }

        if (BaseEnum.class.isAssignableFrom(eClass)) {
            // 如果是BaseEnum的实现类，就用自定义的MessageStatusTypeHandler
            defaultTypeHandler = new MessageStatusTypeHandler();
        }
        else {
            // 默认的枚举转换类用的是 EnumTypeHandler，我们这里指定为EnumOrdinalTypeHandler
            defaultTypeHandler = new EnumOrdinalTypeHandler(eClass);
        }

    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, E e, JdbcType jdbcType) throws SQLException {
        defaultTypeHandler.setNonNullParameter(preparedStatement, i, e, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return defaultTypeHandler.getNullableResult(resultSet, s);
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return defaultTypeHandler.getNullableResult(resultSet, i);
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return defaultTypeHandler.getNullableResult(callableStatement, i);
    }
}
