package com.axgrid.jdbc.rawsql;

import java.util.List;

@RawDAO
public interface MyRawDAO {

    @RawDAO.RawUpdate("update my_table set name=:name, age=:age where id=:id")
    MyRawObject updateObject(@RawDAO.RawParamObject MyRawObject obj);

    @RawDAO.RawInsert("insert into my_table (name, age) VALUES (:name, :age)")
    long createObject(@RawDAO.RawParamObject MyRawObject obj);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    MyRawObject getById(@RawDAO.RawParam("id") long id);

    @RawDAO.RawQuery("select age from my_table where age=:age")
    long getAgeById(long id);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    List<MyRawObject> getByAge(long id);


}
