package com.axgrid.jdbc.rawsql;

@RawDAO
public interface MyRawDAO {

    @RawDAO.RawUpdate("update my_table set name=:name, age=:age where id=:id")
    void updateObject(@RawDAO.RawParamObject MyRawDAO obj);

}
