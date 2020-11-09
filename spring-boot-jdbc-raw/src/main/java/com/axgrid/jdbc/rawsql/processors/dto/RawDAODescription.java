package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawDAO;
import com.axgrid.jdbc.rawsql.RawObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Описание DAO
 */
public class RawDAODescription {

    private String packageName;
    private String objectName;
    private RawDAO rawDAO;

    private final List<RawDAOMethod> methods = new ArrayList<>();

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
