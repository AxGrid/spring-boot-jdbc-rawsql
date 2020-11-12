package com.axgrid.jdbc.rawsql;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RawDAO
public interface MyRawDAO {

    @RawDAO.RawUpdate("update my_table set name=:name, age=:age where id=:id")
    MyRawObject updateObject(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`) " +
            "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time)")
    long createObject(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`) " +
            "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time)")
    MyRawObject createObject2(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    Optional<MyRawObject> getOptionalById(long id);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    MyRawObject getById(@RawParam("id") long id);

    @RawDAO.RawQuery("select * from my_table where age>:age")
    List<MyRawObject> getByAge(int age);

    @RawDAO.RawQuery("select * from my_table where enum2=:e")
    List<MyRawObject> getByEnum2(@RawParam.EnumToOrdinal MySimpleEnum e);

    @RawDAO.RawQuery(value = "select * from my_table where id=:id", nullIfObjectEmpty = false)
    MyRawObject getByIdWithException(long id);

    @RawDAO.RawQuery(value = "select enum2 from my_table where id=:id")
    @RawResult.OrdinalToEnum
    MySimpleEnum getEnumById(long id);

    @RawDAO.RawQuery(value = "select age from my_table where id=:id")
    int getAgeById(long id);

    @RawDAO.RawQuery(value = "select `date` from my_table where id=:id")
    Date getDateById(long id);

    @RawDAO.RawQuery(value = "select `data` from my_table where id=:id")
    @RawResult.JsonObject
    MyIncludedJsonObject getObjectById(long id);
}
