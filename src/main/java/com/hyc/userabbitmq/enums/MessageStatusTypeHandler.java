package com.hyc.userabbitmq.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.INTEGER)
public class MessageStatusTypeHandler <E extends BaseEnum> extends BaseTypeHandler<E> {

    private Class<E> type;

    public MessageStatusTypeHandler(Class<E> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BaseEnum baseEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, baseEnum.getValue());
    }

    @Override
    public E getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.wasNull() ? null : getEnumInstance(resultSet.getInt(s));
    }

    @Override
    public E getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? null : getEnumInstance(resultSet.getInt(i));
    }

    @Override
    public E getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.wasNull() ? null : getEnumInstance(callableStatement.getInt(i));
    }

    private E getEnumInstance(int value) {
        return BaseEnumUtil.codeOf(type, value);
    }
}
