package com.axgrid.jdbc.rawsql.processors.dto;

import java.util.*;

public class RawObjectFieldList extends HashMap<String, RawObjectField> {

    public RawObjectField get(String name) {
        if (this.containsKey(name)) return super.get(name);
        RawObjectField field = new RawObjectField();
        field.setName(name);
        super.put(name, field);
        return field;
    }

    public boolean hasJsonObjects() {
        return this.values().stream().anyMatch(RawObjectField::isJsonObject);
    }

}

