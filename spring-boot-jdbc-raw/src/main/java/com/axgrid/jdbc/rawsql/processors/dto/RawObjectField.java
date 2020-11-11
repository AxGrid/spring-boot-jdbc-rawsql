package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Поле в объекте
 */
public class RawObjectField {
    String name;
    String type;
    String fieldName;
    boolean setter = false;
    boolean getter = false;
    boolean field = false;

    boolean include;
    boolean exclude;
    boolean jsonObject = false;

    String valueProcessor = null;
    final List<String> valueProcessorArguments = new ArrayList<>();

    public List<String> getValueProcessorArguments() {
        return valueProcessorArguments;
    }

    public boolean isGetter() { return getter; }

    public void setGetter(boolean getter) {
        this.getter = getter;
    }

    public String getFromResultSet() {
        if (type == null) throw new RuntimeException("RawSQL Type of null");
        return RawUtils.getFromResultSet(getFieldName(), type);
    }

    public String getMethodName() {
        return "set" + StringUtils.capitalize(name);
    }

    public String getValueProcessor() {
        return valueProcessor;
    }

    public void setValueProcessor(String valueProcessor) {
        this.valueProcessor = valueProcessor;
    }

    public String getDefault() {
        return RawUtils.getTypeDefaultValue(type);
    }

    public String getObjectType() {
        return RawUtils.isSimpleType(type) ? RawUtils.simpleToObject(type) : type;
    }

    @Override
    public String toString() {
        return String.format("RawObjectField(%s:%s, DB:%s, SF:(%s/%s) IE:(%s/%s) Ser:(%s)]",
                name, type, fieldName, RawUtils.flag(setter), RawUtils.flag(field), RawUtils.flag(include), RawUtils.flag(exclude), RawUtils.flag(jsonObject));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RawObjectField field = (RawObjectField) o;
        return Objects.equals(name, field.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public boolean isJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(boolean jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getFieldName() { return fieldName == null || fieldName.equals("") ? name : fieldName; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isSetter() {
        return setter;
    }

    public void setSetter(boolean setter) {
        this.setter = setter;
    }

    public boolean isField() {
        return field;
    }

    public void setField(boolean field) {
        this.field = field;
    }

    public boolean isInclude() {
        return include;
    }

    public void setInclude(boolean include) {
        this.include = include;
    }

    public boolean isExclude() {
        return exclude;
    }

    public void setExclude(boolean exclude) {
        this.exclude = exclude;
    }
}
