package com.axgrid.jdbc.rawsql.processors.dto;


public class RawDAOQueryMethod extends RawDAOMethod {
    boolean nullIfObjectEmpty = true;
    String mapperType = null;

    public boolean isNullIfObjectEmpty() {
        return nullIfObjectEmpty;
    }

    public void setNullIfObjectEmpty(boolean nullIfObjectEmpty) {
        this.nullIfObjectEmpty = nullIfObjectEmpty;
    }

    public String getMapperType() {
        return mapperType;
    }

    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    @Override
    public String getMethod() {
        return "query";
    }

}
