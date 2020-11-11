package com.axgrid.jdbc.rawsql;


import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@RawObject
public class MyRawObject {
    Long id;
    String name;
    int age;

    @RawObject.Include("enum1")
    @RawObject.EnumToString
    MySimpleEnum enumString = MySimpleEnum.No;

    @RawObject.Include("enum2")
    @RawObject.EnumToOrdinal
    MySimpleEnum enumInt = MySimpleEnum.No;

    @RawObject.Include("data")
    @RawObject.JsonObject
    MyIncludedJsonObject includedJsonObject;

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

    double dval = 5.3;

    @RawObject.Exclude
    int excludeField = 15;
}
