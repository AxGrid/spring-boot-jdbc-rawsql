package com.axgrid.jdbc.rawsql;


import com.axgrid.proto.AxPProtoObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.axgrid.proto.AxPPlatform;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RawObject
public class MyRawObject implements Serializable {
    Long id;
    String name;
    int age;

    public String getName() {
        if (name == null && id != null) return String.format("Player%d", (id + 10000));
        return name;
    }

    @RawObject.Include("enum1")
    @RawObject.EnumToString
    MySimpleEnum enumString = MySimpleEnum.No;

    @RawObject.Include("enum2")
    @RawObject.EnumToOrdinal
    MySimpleEnum enumInt = MySimpleEnum.No;

    @RawObject.Include("data")
    @RawObject.JsonObject
    MyIncludedJsonObject includedJsonObject;

    @RawObject.ProtoObject
    @RawObject.Include("proto_object_builder")
    AxPProtoObject.Builder protoObjectBuilder;

    @RawObject.ProtoObject
    @RawObject.Include("proto_object")
    AxPProtoObject protoObject;


    @RawObject.Include("date")
    Date time = new Date();

    @RawObject.Include("longDate")
    @RawObject.DateToLong
    public Date getLongDate() { return time; }

    @RawObject.Include("stringDate")
    @RawObject.DateToString
    public Date getStringDate() { return time; }

    public void setStringDate(Date date) {
    }

    @RawObject.EnumToString
    AxPPlatform platform = AxPPlatform.Linux;

    @JsonIgnore
    @RawObject.Include("platform_int")
    @RawObject.EnumToInteger
    AxPPlatform platformInt = AxPPlatform.Windows;

    double dval = 5.3;


    @JsonIgnore
    @RawObject.Exclude
    int excludeField = 15;

    @RawObject.Exclude
    public boolean isVip() {
        return dval > new Date().getTime();
    }


    public static MyRawObject create() {
        return null;
    }

    public static class Mapper implements RowMapper<MyRawObject> {

        @Override
        public MyRawObject mapRow(ResultSet resultSet, int i) throws SQLException {
            return null;
        }
    }
}
