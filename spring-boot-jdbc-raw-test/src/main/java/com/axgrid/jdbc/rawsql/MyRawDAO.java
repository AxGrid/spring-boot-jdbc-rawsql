package com.axgrid.jdbc.rawsql;

import com.axgrid.proto.AxPProtoObject;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RawDAO
public interface MyRawDAO {

    @RawDAO.RawUpdate(value = "update my_table set name=:name, age=:age where id=:id")
    @RawSpring.Cacheable(value = {"hello", "world"}, key="5")
    MyRawObject updateObject(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`, platform, platform_int) " +
            "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time, :platform, :platformInt)")
    long createObject(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`, platform, platform_int) " +
            "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time, :platform, :platformInt)")
    MyRawObject createObject2(@RawParam.RawParamObject MyRawObject obj);

    @RawDAO.RawQuery("select * from my_table where id=:id")
    Optional<MyRawObject> getOptionalById(long id);

    @RawDAO.RawQuery("select age from my_table where id=:id")
    Optional<Integer> getOptionalAgeById(long id);

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

    @RawDAO.RawQuery(value = "select enum2 from my_table where id=:id", mapper = DemoMapper.class)
    @RawResult.OrdinalToEnum
    MyRawObject getByIdWithMapper(long id);

    @RawDAO.RawQuery(value = "select age from my_table where id=:id")
    @RawSpring.Cacheable("my cache")
    int getAgeById(long id);

    @RawDAO.RawQuery(value = "select `date` from my_table where id=:id")
    Date getDateById(long id);

    @RawDAO.RawQuery(value = "select `data` from my_table where id=:id")
    @RawResult.JsonObject
    @RawSpring.Caching(
            evict = {
                    @RawSpring.CacheEvict(value = "my name", key = "#id"),
                    @RawSpring.CacheEvict(cacheNames = "my name 2", key = "#id2")
            },
            put = {
                    @RawSpring.CachePut(cacheNames = "my name", key = "#id")
            }
    )
    MyIncludedJsonObject getObjectById(long id);

    @RawDAO.RawUpdate("DELETE FROM my_table")
    @RawSpring.Scheduled(fixedDelay = 10000)
    void clear();

    @RawDAO.RawQuery(value = "select proto_object_builder from my_table where id=:id")
    @RawResult.ProtoObject
    AxPProtoObject.Builder getProtoBuilderById(long id);

    @RawDAO.RawQuery(value = "select proto_object from my_table where id=:id")
    @RawResult.ProtoObject
    AxPProtoObject getProtoById(long id);

}
