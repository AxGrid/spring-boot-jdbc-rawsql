package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;
import com.axgrid.jdbc.rawsql.RawObject;
import com.axgrid.jdbc.rawsql.RawParam;
import com.axgrid.jdbc.rawsql.RawUtils;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RawDAOMethod {

    String method;
    String name;
    String type;
    String returnType;
    String query;

    RawObjectDescription rawObject;

    List<RawDAOMethodParameter> parameters = new ArrayList<>();


    public static List<RawDAOMethodParameter> getParameters(ExecutableElement executableElement) {
        List<RawDAOMethodParameter> res = new ArrayList<>();
        for(var item : executableElement.getParameters()) {
            var parameter = new RawDAOMethodParameter();
            parameter.setElement(item);
            parameter.setName(item.getSimpleName().toString());
            parameter.setType(item.asType().toString());
            parameter.setRawParamObject(item.getAnnotation(RawDAO.RawParamObject.class));
            parameter.setRawParam(item.getAnnotation(RawParam.class));

            if (item.getAnnotation(RawParam.JsonObject.class) != null)
                parameter.setValueProcessor("json");

            var enumToInteger = item.getAnnotation(RawParam.EnumToInteger.class);
            if (enumToInteger != null) {
                parameter.setValueProcessor("enumToInt");
                parameter.getValueProcessorArguments().add(enumToInteger.getter());
                parameter.getValueProcessorArguments().add(enumToInteger.setter());
            }

            var enumToString = item.getAnnotation(RawParam.EnumToString.class);
            if (enumToString != null) {
                parameter.setValueProcessor("enumToString");
                parameter.getValueProcessorArguments().add(enumToString.getter());
                parameter.getValueProcessorArguments().add(enumToString.setter());
            }

            if (item.getAnnotation(RawParam.EnumToOrdinal.class) != null) {
                parameter.setValueProcessor("enumToOrdinal");
            }

            if (item.getAnnotation(RawParam.DateToLong.class) != null) {
                parameter.setValueProcessor("dateToLong");
            }

            var dateToString = item.getAnnotation(RawParam.DateToString.class);
            if (dateToString != null) {
                parameter.setValueProcessor("dateToString");
                parameter.getValueProcessorArguments().add(dateToString.format());
            }

            var processor = item.getAnnotation(RawParam.Processor.class);
            if (processor != null) {
                parameter.setValueProcessor(processor.value() + processor.name());
                parameter.getValueProcessorArguments().addAll(Arrays.asList(processor.params()));
            }


            res.add(parameter);
        }
        return res;
    }

    public int  getProcessorFieldsSize() {
        return getProcessorFields().size();
    }

    public int  getFieldsSize() {
        return rawObject.getFieldsList().size();
    }

    public String getFieldsString() {
        return rawObject.getFieldsList().stream().map(field -> String.format("T:%s, N:%s, J:%s, VP:%s, ",
                field.getType(), field.getFieldName(), field.jsonObject,
                field.valueProcessor)).collect(Collectors.joining("\n"));
    }


    public List<RawObjectField> getProcessorFields() {
        var pf = rawObject.getFieldsList()
                .stream()
                .filter(item -> item.getValueProcessor() != null && !item.getValueProcessor().equals(""))
                .collect(Collectors.toList());
        return pf;
    }

    public RawObjectDescription getRawObject() {
        return rawObject;
    }

    public void setRawObject(RawObjectDescription rawObject) {
        this.rawObject = rawObject;
    }

    public Element getRawObjectElement() {
        var param = this.parameters.stream().filter(RawDAOMethodParameter::isRawParamObject).findFirst().orElse(null);
        if (param == null) return null;
        return param.element;
    }

    public String getFlatParameters() {
        return parameters.stream().map(RawDAOMethodParameter::toString).collect(Collectors.joining(", "));
    }

    public String getSimpleReturn() {
        return RawUtils.objectToSimple(returnType);
    }

    public boolean isReturnRawObject() {
        var rawParamObjectArgument = parameters.stream().filter(RawDAOMethodParameter::isRawParamObject).findFirst().orElse(null);
        if (rawParamObjectArgument == null) return false;
        return returnType.equals(rawParamObjectArgument.type);
    }

    public String getRawObjectName() {
        var rawParamObjectArgument = parameters.stream().filter(RawDAOMethodParameter::isRawParamObject).findFirst().orElse(null);
        if (rawParamObjectArgument == null) return null;
        return rawParamObjectArgument.name;
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
