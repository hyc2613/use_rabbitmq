package com.hyc.userabbitmq.enums;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageStatusTypeHandler <E extends BaseEnum> extends BaseTypeHandler<BaseEnum> {

    Class<E> eClass;

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BaseEnum baseEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, baseEnum.getValue());
    }

    @Override
    public BaseEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.wasNull() ? null : getEnumInstance(resultSet.getInt(s));
    }

    @Override
    public BaseEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.wasNull() ? null : getEnumInstance(resultSet.getInt(i));
    }

    @Override
    public BaseEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.wasNull() ? null : getEnumInstance(callableStatement.getInt(i));
    }

    private E getEnumInstance(int value) {
        return BaseEnumUtil.codeOf(eClass, value);
    }
}
