package com.axgrid.jdbc.rawsql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DemoMapper implements RowMapper<MyRawObject> {
    @Override
    public MyRawObject mapRow(ResultSet resultSet, int i) throws SQLException {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            var r = om.readValue(resultSet.getString(""), DemoMapper.class);
        }catch (JsonProcessingException e) {
        }
        return null;
    }
}
