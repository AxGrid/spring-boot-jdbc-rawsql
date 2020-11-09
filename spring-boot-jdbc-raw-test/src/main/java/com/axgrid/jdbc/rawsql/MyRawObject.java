package com.axgrid.jdbc.rawsql;

import lombok.Data;

@Data
@RawObject
public class MyRawObject {
    Long id;
    String name;

    int age;

    @RawObject.Include("enum")
    MySimpleEnum quest = MySimpleEnum.No;

    @RawObject.Include("data")
    @RawObject.JsonObject
    MyIncludedJsonObject includedJsonObject;

    double doubleValue = 5.3;

    @RawObject.Exclude
    int excludeField = 15;
}
