package com.axgrid.jdbc.rawsql.processors.dto;

public class RawDAOSaveMethod extends RawDAOMethod {
    String create;
    String update;
    String id;

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
