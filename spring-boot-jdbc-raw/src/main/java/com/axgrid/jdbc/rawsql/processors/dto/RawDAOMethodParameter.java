package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;
import com.axgrid.jdbc.rawsql.RawParam;
import org.w3c.dom.ls.LSOutput;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RawDAOMethodParameter {
    String name;
    String type;
    Element element;
    RawParam rawParam;
    RawParam.RawParamObject rawParamObject;
    String valueProcessor = null;
    final List<String> valueProcessorArguments = new ArrayList<>();

    final Pattern mapperPattern =  Pattern.compile("org.springframework.jdbc.core.RowMapper<(.*)>$");

    public Types typeUtils;

    public boolean isRowMapper() {

        TypeElement tu = (TypeElement)typeUtils.asElement(element.asType());

        if (tu == null) return false;
        return tu.getInterfaces().stream().map(item -> item.toString()).anyMatch(name -> mapperPattern.matcher(name).find());
    }


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

    public RawParam.RawParamObject getRawParamObject() {
        return rawParamObject;
    }

    public void setRawParamObject(RawParam.RawParamObject rawParamObject) {
        this.rawParamObject = rawParamObject;
    }
}
