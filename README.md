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
@RawDAO.RawInsert("insert into my_table " +
 "(`name`, age, enum1, enum2, `data`, dval, longDate, stringDate, `date`) " +
 "VALUES (:name, :age, :enumString, :enumInt, :includedJsonObject, :dval, " +
 ":longDate, :stringDate, :time)")
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

Attributes and helpers
----------------------
**@RawObject** - create mapper for this object
  
 * onlyIncludedFields - flag, exclude all fields
 * includeFields - include field list
 * excludedFields - exclude field list
 * useFieldsOnly - flag, don't user setter/getter
 
**@RawObject.Include** - include this field or method

 * fieldName - set database field name for mapper

**@RawObject.Exclude** - exclude this field or method

**@RawObject.JsonObject** - mark field for auto create String-JSON serialization in database

**@RawObject.ProtoObject** - mark field for auto create Binary-Protobuf serialization in database

**@RawObject.EnumToString** - cast enum to String into database

 * getter - set method for get String value (default toString) 
 * setter - set static-method for set String value (default valueOf)

**@RawObject.EnumToInteger** - cast enum to Integer into database
 
 * getter - set method for get String value (default getNumber) 
 * setter - set static-method for set String value (default forNumber)

**@RawObject.EnumToOrdinal** - cast enum to ordinal-integer into database

**@RawObject.DateToString** - cast Date to String into database
 * format - set format (default "yyyy-MM-dd hh:mm:ss")

**@RawObject.DateToLong** - cast Date to long into database
