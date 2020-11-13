package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Описание DAO
 */
public class RawDAODescription {

    private String packageName;
    private String objectName;
    private RawDAO rawDAO;

    RawDAOCacheAnnotationCollection cache;

    public RawDAOCacheAnnotationCollection getCache() {
        return cache;
    }

    public void setCache(RawDAOCacheAnnotationCollection cache) {
        this.cache = cache;
    }

    public String getClassName() { return objectName + "RawDAO"; }

    private final List<RawDAOMethod> methods = new ArrayList<>();

    public List<RawDAOMethod> getUpdateMethods() {
        return methods.stream().filter(item -> item.method.equals("update")).collect(Collectors.toList());
    }

    public List<RawDAOMethod> getQueryMethods() {
        return methods.stream().filter(item -> item.method.equals("query")).collect(Collectors.toList());
    }

    public List<RawDAOMethod> getInsertMethods() {
        return methods.stream().filter(item -> item.method.equals("insert")).collect(Collectors.toList());
    }

    public List<RawDAOMethod> getSaveMethods() {
        return methods.stream().filter(item -> item.method.equals("save")).collect(Collectors.toList());
    }

    public List<RawDAOMethod> getUndefinedMethods() {
        return methods.stream().filter(item -> item.method.equals("undefined")).collect(Collectors.toList());
    }


    public List<RawDAOMethod> getMethods() {
        return methods;
    }

    public RawDAO getRawDAO() {
        return rawDAO;
    }

    public void setRawDAO(RawDAO rawDAO) {
        this.rawDAO = rawDAO;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }


}
