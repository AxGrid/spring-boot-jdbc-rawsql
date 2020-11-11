![RawSQL Logo](./logo.png)

RawSQL
======

spring-boot-jdbc helper for mapper and sql-dao creation.


Simple usage
------------
```java

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

```


```java

// Create object and return ID
@RawDAO.RawInsert("insert into my_table (`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`) " +
        "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, :longDate, :stringDate, :time)")
long createObject(@RawDAO.RawParamObject MyRawObject obj);

// Update age in object
@RawDAO.RawUpdate("update my_table set name=:name, age=:age where id=:id")
MyRawObject updateObject(@RawDAO.RawParamObject MyRawObject obj);

// Get by age
@RawDAO.RawQuery("select * from my_table where age>:age")
List<MyRawObject> getByAge(int age);

// Get only from from database 
@RawDAO.RawQuery(value = "select enum2 from my_table where id=:id")
@RawResult.OrdinalToEnum
MySimpleEnum getEnumById(long id);

```
