package com.hyc.userabbitmq.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.EnumOrdinalTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.jetbrains.annotations.NotNull;

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

    public AutoEnumTypeHandler(@NotNull Class<E> eClass) {
        if (eClass == null) {
            throw new IllegalArgumentException();
        }
        if (BaseEnum.class.isAssignableFrom(eClass)) {
            defaultTypeHandler = new MessageStatusTypeHandler(eClass);
        }
        else {
            defaultTypeHandler = new EnumOrdinalTypeHandler(eClass);
        }
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        defaultTypeHandler.setNonNullParameter(ps, i, parameter, jdbcType);
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return defaultTypeHandler.getNullableResult(rs, columnName);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return defaultTypeHandler.getNullableResult(rs, columnIndex);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return defaultTypeHandler.getNullableResult(cs, columnIndex);
    }
}
