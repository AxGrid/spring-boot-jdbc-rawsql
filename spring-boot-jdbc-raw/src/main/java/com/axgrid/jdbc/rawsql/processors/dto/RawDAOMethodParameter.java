package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;

import javax.lang.model.element.Element;

public class RawDAOMethodParameter {
    String name;
    String type;
    Element element;
    RawDAO.RawParam rawParam;
    RawDAO.RawParamObject rawParamObject;

    @Override
    public String toString() {
        return String.format("%s %s", type, name);
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public boolean isRawParamObject() {
        return rawParamObject != null;
    }

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
