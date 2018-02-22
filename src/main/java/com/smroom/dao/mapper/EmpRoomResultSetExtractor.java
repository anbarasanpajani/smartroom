package com.smroom.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.smroom.bean.EmpRoom;

public class EmpRoomResultSetExtractor implements ResultSetExtractor {

    @Override
    public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
        EmpRoom empRoom = new EmpRoom();
        empRoom.setEmpId(rs.getLong(2));
        empRoom.setRoomId(rs.getLong(3));
        empRoom.setInout(rs.getString(4));
        return empRoom;
    }

}