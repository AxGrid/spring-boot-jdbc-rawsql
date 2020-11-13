package com.axgrid.jdbc.rawsql;

import com.github.jknack.handlebars.internal.lang3.StringUtils;

import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RawUtils {
    static Pattern genericPattern =  Pattern.compile("^(.*)<(.*)>$");
    static Pattern setterPattern =  Pattern.compile("^set(\\w+)$");
    static Pattern getterPattern =  Pattern.compile("^get(\\w+)$");
    static Pattern builderPattern =  Pattern.compile("(.*).Builder$");

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

    final static Set<String> baseMethods = new HashSet<>(Arrays.asList(
       "equals",
       "toString",
       "hashCode",
       "annotationType"
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
            return isPrimitiveOrWrapper(Class.forName(typeName));
        }catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isList(String typeName) {
        return typeName.contains("List<");
    }
    public static boolean isOptional(String typeName) {
        return typeName.contains("java.util.Optional<");
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
        var matcher = genericPattern.matcher(listType);
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

    public static boolean isBuilder(String typeName) {
        var mather = builderPattern.matcher(typeName);
        return mather.find();
    }

    public static String getBuilderType(String typeName) {
        var mather = builderPattern.matcher(typeName);
        if (mather.find()) return mather.group(1);
        return null;
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

    private static final Map<Class<?>, Class<?>> primitiveWrapperMap;
    private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap;


    public static boolean isPrimitiveOrWrapper(Class<?> type) {
        if (type == null) {
            return false;
        } else {
            return type.isPrimitive() || isPrimitiveWrapper(type);
        }
    }

    public static boolean isPrimitiveWrapper(Class<?> type) {
        return wrapperPrimitiveMap.containsKey(type);
    }


    static {
        primitiveWrapperMap = new HashMap();
        primitiveWrapperMap.put(Boolean.TYPE, Boolean.class);
        primitiveWrapperMap.put(Byte.TYPE, Byte.class);
        primitiveWrapperMap.put(Character.TYPE, Character.class);
        primitiveWrapperMap.put(Short.TYPE, Short.class);
        primitiveWrapperMap.put(Integer.TYPE, Integer.class);
        primitiveWrapperMap.put(Long.TYPE, Long.class);
        primitiveWrapperMap.put(Double.TYPE, Double.class);
        primitiveWrapperMap.put(Float.TYPE, Float.class);
        primitiveWrapperMap.put(Void.TYPE, Void.TYPE);
        wrapperPrimitiveMap = new HashMap<>();

        Iterator var0 = primitiveWrapperMap.entrySet().iterator();
        while(var0.hasNext()) {
            Map.Entry<Class<?>, Class<?>> entry = (Map.Entry)var0.next();
            Class<?> primitiveClass = (Class)entry.getKey();
            Class<?> wrapperClass = (Class)entry.getValue();
            if (!primitiveClass.equals(wrapperClass)) {
                wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
            }
        }
    }

    public static String arrayOrSingleString(Object[] array) {
        if (array.length == 0) return "\"\"";
        if (array.length == 1) return String.format("\"%s\"", array[0].toString());
        return String.format("{%s}", Arrays.asList(array).stream().map(item -> String.format("\"%s\"", item.toString())).collect(Collectors.joining(", ")));
    }

    public static String getAnnotationValue(Object o) {
        if (o == null) return "null";
        if (o instanceof String) return "\""+ o.toString() + "\"";
        if (o instanceof String[]) return arrayOrSingleString((String[])o);
        if (o instanceof Boolean) return o.toString();
        if (o instanceof Integer || o instanceof Long) return o.toString();
        return "null";
    }

    public static List<Method> excludeBaseClassMethods(Method[] methods) {
        return Arrays.stream(methods).filter(method -> method.getParameterCount() == 0 && !baseMethods.contains(method.getName())).collect(Collectors.toList());
    }

    public static boolean annotationMethodHasValue(Object annotation, Method method) {
        try {
            var result = method.invoke(annotation);
            if (result instanceof Long) return true;
            if (result instanceof Boolean) return true;
            if (result instanceof String) return !result.equals("");
            if (result instanceof String[]) return ((String[])result).length > 0;
        } catch (IllegalAccessException | InvocationTargetException e) {
            return false;
        }
        return false;
    }



    public static String collectAllAnnotationParams(Object annotation) {
         var methods = excludeBaseClassMethods(annotation.getClass().getDeclaredMethods());
         List<String> parameters = new ArrayList<>();
         for(var method : methods) {
             if (!annotationMethodHasValue(annotation, method)) continue;
             Object result = null;

             try {
                 result = method.invoke(annotation);
             } catch (IllegalAccessException | InvocationTargetException e) {
                 e.printStackTrace();
             }
             String parameter = String.format("%s = %s", method.getName(), getAnnotationValue(result));
             parameters.add(parameter);
         }
         return String.join(", ", parameters);
    }
}
