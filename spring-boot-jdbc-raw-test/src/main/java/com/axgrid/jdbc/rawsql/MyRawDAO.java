package com.axgrid.jdbc.rawsql;

import java.util.List;

@RawDAO
public interface MyRawDAO {

    @RawDAO.RawUpdate("update my_table set name=:name, age=:age where id=:id")
    MyRawObject updateObject(@RawDAO.RawParamObject MyRawObject obj);

//    id bigint primary key auto_increment not null,
//            `name` varchar(150),
//    age int,
//            `enum1` varchar(100),
//    `enum2` int,
//            `data` longtext,
//            `dval` double

    @RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`) " +
            "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time)")
    long createObject(@RawDAO.RawParamObject MyRawObject obj);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    MyRawObject getById(@RawDAO.RawParam("id") long id);

    @RawDAO.RawQuery("select age from my_table where age=:age")
    long getAgeById(long id);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    List<MyRawObject> getByAge(long id);

    @RawDAO.RawQuery("select * from my_table where id=:id and enum2=:e")
    List<MyRawObject> getByAgeAndEnum2(long id, MySimpleEnum e);

    @RawDAO.RawQuery("select * from my_table where id=:id and enum1=:e")
    List<MyRawObject> getByAgeAndEnum1(long id, MySimpleEnum e);

}
