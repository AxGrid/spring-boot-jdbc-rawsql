package com.axgrid.jdbc.rawsql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
@RawObject
public class MyRawObject {
    Long id;
    String name;
    int age;

    @RawObject.Include("enum1")
    MySimpleEnum enumString = MySimpleEnum.No;

    @RawObject.Include("enum2")
    MySimpleEnum enumInt = MySimpleEnum.No;

    public int getEnumIntValue() {
        return enumInt.ordinal();
    }

    @RawObject.Include("data")
    @RawObject.JsonObject
    MyIncludedJsonObject includedJsonObject;

    public String getIncludedJsonObjectData() {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(includedJsonObject);
        }catch (JsonProcessingException e) {
            return null;
        }
    }

    double dval = 5.3;

    @RawObject.Exclude
    int excludeField = 15;
}
