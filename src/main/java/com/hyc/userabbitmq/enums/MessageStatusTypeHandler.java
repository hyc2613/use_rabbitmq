package com.hyc.userabbitmq.enums;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageStatusTypeHandler implements TypeHandler<MessageStatusEnum> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, MessageStatusEnum messageStatusEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, messageStatusEnum.getValue());
    }

    @Override
    public MessageStatusEnum getResult(ResultSet resultSet, String s) throws SQLException {
        int status = resultSet.getInt(s);
        return MessageStatusEnum.valueOf(status);
    }

    @Override
    public MessageStatusEnum getResult(ResultSet resultSet, int i) throws SQLException {
        int status = resultSet.getInt(i);
        return MessageStatusEnum.valueOf(status);
    }

    @Override
    public MessageStatusEnum getResult(CallableStatement callableStatement, int i) throws SQLException {
        int status = callableStatement.getInt(i);
        return MessageStatusEnum.valueOf(status);
    }
}
