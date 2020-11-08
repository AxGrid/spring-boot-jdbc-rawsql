package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawObject;
import com.axgrid.jdbc.rawsql.RawUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Класс описывающий RawObject для создания маппера
 */
public class RawObjectDescription {

    private String packageName;
    private String objectName;
    private RawObject rawObject;

    public String getClassName() { return objectName + "RawObjectMapper"; }

    final RawObjectFieldList fields = new RawObjectFieldList();

    public List<RawObjectField> getFieldsList() {
        return new ArrayList<>(fields.values());
    }


    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public RawObject getRawObject() {
        return rawObject;
    }

    public void setRawObject(RawObject rawObject) {
        this.rawObject = rawObject;
    }

    public RawObjectFieldList getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return String.format("RawObjectDescription(%s:%s)\n%s", packageName, objectName, fields);
    }

}
