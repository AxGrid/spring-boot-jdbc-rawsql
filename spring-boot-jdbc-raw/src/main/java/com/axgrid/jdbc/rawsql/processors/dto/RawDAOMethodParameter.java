package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;

public class RawDAOMethodParameter {
    String name;
    String type;
    RawDAO.RawParam rawParam;
    RawDAO.RawParamObject rawParamObject;

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

    public RawDAO.RawParam getRawParam() {
        return rawParam;
    }

    public void setRawParam(RawDAO.RawParam rawParam) {
        this.rawParam = rawParam;
    }

    public RawDAO.RawParamObject getRawParamObject() {
        return rawParamObject;
    }

    public void setRawParamObject(RawDAO.RawParamObject rawParamObject) {
        this.rawParamObject = rawParamObject;
    }
}
