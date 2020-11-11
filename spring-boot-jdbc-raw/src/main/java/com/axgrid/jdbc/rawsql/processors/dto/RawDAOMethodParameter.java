package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;
import com.axgrid.jdbc.rawsql.RawParam;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.List;

public class RawDAOMethodParameter {
    String name;
    String type;
    Element element;
    RawParam rawParam;
    RawDAO.RawParamObject rawParamObject;
    String valueProcessor = null;
    final List<String> valueProcessorArguments = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("%s %s", type, name);
    }

    public String getValueProcessor() {
        return valueProcessor;
    }

    public void setValueProcessor(String valueProcessor) {
        this.valueProcessor = valueProcessor;
    }

    public List<String> getValueProcessorArguments() {
        return valueProcessorArguments;
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

    public RawParam getRawParam() {
        return rawParam;
    }

    public void setRawParam(RawParam rawParam) {
        this.rawParam = rawParam;
    }

    public RawDAO.RawParamObject getRawParamObject() {
        return rawParamObject;
    }

    public void setRawParamObject(RawDAO.RawParamObject rawParamObject) {
        this.rawParamObject = rawParamObject;
    }
}
