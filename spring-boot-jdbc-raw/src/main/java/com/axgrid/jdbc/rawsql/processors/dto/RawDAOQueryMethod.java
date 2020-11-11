package com.axgrid.jdbc.rawsql.processors.dto;


import com.axgrid.jdbc.rawsql.RawUtils;

public class RawDAOQueryMethod extends RawDAOMethod {
    boolean nullIfObjectEmpty = true;
    String mapperType = null;

    public boolean isNullIfObjectEmpty() {
        return nullIfObjectEmpty;
    }

    public String getMapper() {
        if (mapperType != null) return String.format("new %s()", mapperType);
        if (RawUtils.isPrimitiveOrWrapper(returnType)) return String.format("%s.class", RawUtils.isSimpleType(returnType) ? RawUtils.simpleToObject(returnType) : returnType);
        if (RawUtils.isDirectSQLClass(returnType)) return String.format("%s.class", returnType);
        if (isList()) return String.format("new %sRawObjectMapper()", RawUtils.getGenericTypeName(returnType));
        return String.format("new %sRawObjectMapper()", returnType);
    }

    public String getNull() {
        return RawUtils.getTypeDefaultValue(returnType);
    }

    public void setNullIfObjectEmpty(boolean nullIfObjectEmpty) {
        this.nullIfObjectEmpty = nullIfObjectEmpty;
    }

    public String getMapperType() {
        return mapperType;
    }

    public boolean isList() {
        return RawUtils.isList(this.returnType);
    }

    public String getListGenericType() {
        return RawUtils.getGenericTypeName(this.returnType);
    }


    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    @Override
    public String getMethod() {
        return "query";
    }

}
