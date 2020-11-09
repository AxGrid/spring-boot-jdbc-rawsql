package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RawDAOMethod {

    String method;
    String name;
    String type;
    String returnType;
    String query;

    List<RawDAOMethodParameter> parameters = new ArrayList<>();

    public static List<RawDAOMethodParameter> getParameters(ExecutableElement executableElement) {
        List<RawDAOMethodParameter> res = new ArrayList<>();
        for(var item : executableElement.getParameters()) {
            var parameter = new RawDAOMethodParameter();
            parameter.setName(item.getSimpleName().toString());
            parameter.setType(item.asType().toString());
            parameter.setRawParamObject(item.getAnnotation(RawDAO.RawParamObject.class));
            parameter.setRawParam(item.getAnnotation(RawDAO.RawParam.class));
            res.add(parameter);
        }
        return res;
    }

    public String getFlatParameters() {
        return parameters.stream().map(RawDAOMethodParameter::toString).collect(Collectors.joining(", "));
    }

    public boolean isReturnRawObject() {
        var rawParamObjectArgument = parameters.stream().filter(RawDAOMethodParameter::isRawParamObject).findFirst().orElse(null);
        if (rawParamObjectArgument == null) return false;
        return returnType.equals(rawParamObjectArgument.type);
    }

    public List<RawDAOMethodParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<RawDAOMethodParameter> parameters) {
        this.parameters = parameters;
    }

    public boolean isVoid() {
        return returnType.equals("void");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
