package com.axgrid.jdbc.rawsql;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

public final class RawUtils {
    static Pattern listPattern =  Pattern.compile("^(.*)<(.*)>$");
    static Pattern setterPattern =  Pattern.compile("^set(\\w+)$");
    static Pattern getterPattern =  Pattern.compile("^get(\\w+)$");

    final static Set<String> primitiveName = new HashSet<>(Arrays.asList(
            "long",
            "short",
            "int",
            "byte",
            "char",
            "boolean",
            "double",
            "float"
    ));

    @FunctionalInterface
    public interface GetClassValue {
        void execute() throws MirroredTypeException, MirroredTypesException;
    }

    public static List<? extends TypeMirror> getTypeMirrorFromAnnotationValue(GetClassValue c) {
        try {
            c.execute();
        }
        catch(MirroredTypesException ex) {
            return ex.getTypeMirrors();
        }
        return null;
    }


    public static String flag(boolean b) {
        return b ? "1" : "0";
    }

    public static boolean isSimpleType(String typeName) {
        return primitiveName.contains(typeName);
    }

    public static boolean isPrimitiveOrWrapper(String typeName) {
        if (isSimpleType(typeName)) return true;
        try {
            return ClassUtils.isPrimitiveOrWrapper(Class.forName(typeName));
        }catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isList(String typeName) {
        return typeName.contains("List<");
    }


    public static boolean isDirectSQLClass(String typeName) {
        switch (typeName){
            default:
                return false;
            case "java.util.Date":
            case "java.lang.String":
                return true;
        }
    }

    public static String getTypeDefaultValue(String typeName) {
        switch (typeName) {
            default:
                return "null";
            case "long":
            case "int":
            case "byte":
            case "float":
            case "double":
            case "short":
                return "0";
            case "char":
                return "\' \'";
            case "boolean":
                return "false";
        }
    }

    public static boolean isNumericType(String typeName) {
        switch (typeName) {
            case "long":
            case "int":
            case "byte":
            case "short":
            case "java.lang.Long":
            case "java.lang.Integer":
            case "java.lang.Byte":
            case "java.lang.Short":
                return true;
            default:
                return false;
        }
    }

    public static String simpleToObject(String typeName) {
        switch (typeName){
            default:
                return "java.lang.Object";
            case "long":
                return "java.lang.Long";
            case "int":
                return "java.lang.Integer";
            case "byte":
                return "java.lang.Byte";
            case "byte[]":
                return "byte[]";
            case "char":
                return "java.lang.Character";
            case "boolean":
                return "java.lang.Boolean";
            case "float":
                return "java.lang.Float";
            case "double":
                return "java.lang.Double";
            case "short":
                return "java.lang.Short";
        }
    }

    public static String objectToSimple(String typeName) {
        switch (typeName){
            default:
                return typeName;
            case "java.lang.Long":
                return "long";
            case "java.lang.Integer":
                return "int";
            case "java.lang.Byte":
                return "byte";
            case "ava.lang.Character":
                return "java.lang.Character";
            case "char":
                return "boolean";
            case "java.lang.Float":
                return "float";
            case "java.lang.Double":
                return "double";
            case "java.lang.Short":
                return "short";
        }
    }

    public static String getGenericTypeName(String listType) {
        var matcher = listPattern.matcher(listType);
        if (matcher.find()) return matcher.group(2);
        return "";
    }

    public static boolean isSetter(String name) {
        return setterPattern.matcher(name).find();
    }

    public static boolean isGetter(String name) {
        return getterPattern.matcher(name).find();
    }

    public static String getSetterName(String name) {
        var matcher = setterPattern.matcher(name);
        if (!matcher.find()) return null;
        return StringUtils.uncapitalize(matcher.group(1));
    }

    public static String getGetterName(String name) {
        var matcher = getterPattern.matcher(name);
        if (!matcher.find()) return null;
        return StringUtils.uncapitalize(matcher.group(1));
    }

    public static String getFromResultSet(String field, String typeName) {
        switch (typeName){
            default:
                return String.format(".getObject(\"%s\", %s.class)", field, typeName);
            case "int":
                return String.format(".getInt(\"%s\")", field);
            case "long":
                return String.format(".getLong(\"%s\")", field);
            case "byte":
                return String.format(".getByte(\"%s\")", field);
            case "boolean":
                return String.format(".getBoolean(\"%s\")", field);
            case "byte[]":
                return String.format(".getBytes(\"%s\")", field);
            case "short":
                return String.format(".getShort(\"%s\")", field);
            case "double":
                return String.format(".getDouble(\"%s\")", field);
            case "float":
                return String.format(".getFloat(\"%s\")", field);
            case "java.lang.String":
                return String.format(".getString(\"%s\")", field);
            case "java.util.Date":
                return String.format(".getTimestamp(\"%s\")", field);
        }
    }

}
