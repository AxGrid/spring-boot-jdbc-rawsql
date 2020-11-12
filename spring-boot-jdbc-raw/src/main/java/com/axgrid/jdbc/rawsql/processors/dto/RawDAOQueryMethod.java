package com.axgrid.jdbc.rawsql.processors.dto;


import com.axgrid.jdbc.rawsql.RawUtils;

public class RawDAOQueryMethod extends RawDAOMethod {
    boolean nullIfObjectEmpty = true;
    String mapperType = null;

    public boolean isNullIfObjectEmpty() {
        return nullIfObjectEmpty;
    }

    public String getMapper() {
        if (this.getParameters().stream().anyMatch(RawDAOMethodParameter::isRowMapper)) return this.getParameters().stream().filter(RawDAOMethodParameter::isRowMapper).map(item -> item.name).findFirst().orElse("__error__");
        if (mapperType != null) return String.format("new %s()", mapperType);
        if (RawUtils.isPrimitiveOrWrapper(returnType)) return String.format("%s.class", RawUtils.isSimpleType(returnType) ? RawUtils.simpleToObject(returnType) : returnType);
        if (isOptional()) return getOptionalType(RawUtils.getGenericTypeName(returnType));
        if (RawUtils.isDirectSQLClass(returnType)) return String.format("%s.class", returnType);
        if (isList()) return String.format("new %sRawObjectMapper()", RawUtils.getGenericTypeName(returnType));
        return String.format("new %sRawObjectMapper()", returnType);
    }

    private String getOptionalType(String optionalType) {
        if (RawUtils.isPrimitiveOrWrapper(optionalType)) return String.format("%s.class", RawUtils.isSimpleType(optionalType) ? RawUtils.simpleToObject(optionalType) : optionalType);
        if (RawUtils.isDirectSQLClass(optionalType)) return String.format("%s.class", optionalType);
        if (isList()) return String.format("new %sRawObjectMapper()", RawUtils.getGenericTypeName(optionalType));
        return String.format("new %sRawObjectMapper()", optionalType);
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

    public boolean isOptional() {
        return RawUtils.isOptional(this.returnType);
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
