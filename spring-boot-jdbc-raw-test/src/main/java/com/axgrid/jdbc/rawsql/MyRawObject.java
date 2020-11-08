package com.axgrid.jdbc.rawsql;

import lombok.Data;

@Data
@RawObject
public class MyRawObject {
    Long id;
    String name;

    @RawObject.Include("enum")
    MySimpleEnum quest = MySimpleEnum.No;

    @RawObject.Include("data")
    MyIncludedJsonObject includedJsonObject;

    double doubleValue = 5.3;

    @RawObject.Exclude
    int excludeField = 15;
}
