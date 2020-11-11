package com.axgrid.jdbc.rawsql;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Data
@RawObject
@Slf4j
public class MyRawObject {
    Long id;
    String name;
    int age;

    @RawObject.Include("enum1")
    MySimpleEnum enumString = MySimpleEnum.No;

    @RawObject.Include("enum2")
    @RawObject.EnumToOrdinal
    MySimpleEnum enumInt = MySimpleEnum.No;

    @RawObject.Include("data")
    @RawObject.JsonObject
    MyIncludedJsonObject includedJsonObject;


    Date time = new Date();

    @RawObject.Include("longDate")
    @RawObject.DateToLong
    public Date getLongDate() { return time; }

    @RawObject.Include("stringDate")
    @RawObject.DateToString
    public Date getStringDate() { return time; }

    public void setStringDate(String date) {
        log.debug("Try insert string date is {}", date);
    }

    double dval = 5.3;

    @RawObject.Exclude
    int excludeField = 15;
}
