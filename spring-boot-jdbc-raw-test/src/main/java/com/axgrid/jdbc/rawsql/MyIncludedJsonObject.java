package com.axgrid.jdbc.rawsql;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

@Data
@NoArgsConstructor
public class MyIncludedJsonObject implements Serializable {


    String textField = "";
    Integer randomField = null;

    public void setRandomField(int value) {
        randomField = value;
    }

    public int getRandomField() {
        if (randomField == null)
           randomField = new Random(new Date().getTime()).nextInt(100)+1;
        return randomField;
    }

    public MyIncludedJsonObject(String text) {
        this.textField = text;
    }

}
