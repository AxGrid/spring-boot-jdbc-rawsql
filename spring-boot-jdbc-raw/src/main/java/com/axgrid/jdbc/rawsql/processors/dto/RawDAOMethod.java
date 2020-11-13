package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.*;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Types;
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
    RawCacheAnnotationCollection cache;
    List<RawDAOMethodParameter> parameters = new ArrayList<>();

    String resultProcessor = null;
    final List<String> resultProcessorArguments = new ArrayList<>();

    public Types typeUtils;

    public void createResultProcessor(ExecutableElement executableElement) {

        if (executableElement.getAnnotation(RawResult.OrdinalToEnum.class)!=null)
            resultProcessor = "enumToOrdinal";
        var integerToEnum = executableElement.getAnnotation(RawResult.IntegerToEnum.class);
        if (integerToEnum!=null) {
            resultProcessor = "enumToInt";
            resultProcessorArguments.add(integerToEnum.setter());
        }
        var stringToEnum = executableElement.getAnnotation(RawResult.StringToEnum.class);
        if (stringToEnum!=null) {
            resultProcessor = "enumToString";
            resultProcessorArguments.add(stringToEnum.setter());
        }

        if (executableElement.getAnnotation(RawResult.LongToDate.class)!=null)
            resultProcessor = "dateToLong";

        var stringToDate = executableElement.getAnnotation(RawResult.StringToDate.class);
        if (stringToDate!=null) {
            resultProcessor = "dateToString";
            resultProcessorArguments.add(stringToDate.format());
        }

        var processor = executableElement.getAnnotation(RawResult.Processor.class);
        if (processor != null) {
            resultProcessor = processor.value() + processor.name();
            resultProcessorArguments.addAll(Arrays.asList(processor.params()));
        }

        if (executableElement.getAnnotation(RawResult.ProtoObject.class) != null)
            resultProcessor =  "proto";

        if (executableElement.getAnnotation(RawResult.JsonObject.class) != null)
            resultProcessor =  "json";
    }

    public RawCacheAnnotationCollection getCache() {
        return cache;
    }

    public void setCache(RawCacheAnnotationCollection cache) {
        this.cache = cache;
    }

    public static List<RawDAOMethodParameter> getParameters(ExecutableElement executableElement, Types typeUtils) {
        List<RawDAOMethodParameter> res = new ArrayList<>();
        for(var item : executableElement.getParameters()) {
            var parameter = new RawDAOMethodParameter();
            parameter.typeUtils = typeUtils;
            parameter.setElement(item);
            parameter.setName(item.getSimpleName().toString());
            parameter.setType(item.asType().toString());
            parameter.setRawParamObject(item.getAnnotation(RawParam.RawParamObject.class));
            parameter.setRawParam(item.getAnnotation(RawParam.class));

            if (item.getAnnotation(RawParam.JsonObject.class) != null)
                parameter.setValueProcessor("json");

            if (item.getAnnotation(RawParam.ProtoObject.class) != null)
                parameter.setValueProcessor("proto");

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

    public String getResultProcessor() { return isParameterMapper() ? null : resultProcessor; }

    public boolean isParameterMapper() { return this.getParameters().stream().anyMatch(RawDAOMethodParameter::isRowMapper); }

    public void setResultProcessor(String resultProcessor) {
        this.resultProcessor = resultProcessor;
    }

    public List<String> getResultProcessorArguments() {
        return resultProcessorArguments;
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
        return parameters.stream().map(item -> item.isRowMapper() ? "/* mapper */ "+item.toString() : item.toString()) .collect(Collectors.joining(", "));
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
