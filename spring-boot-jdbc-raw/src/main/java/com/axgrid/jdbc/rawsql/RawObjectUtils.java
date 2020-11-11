package com.axgrid.jdbc.rawsql;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public final class RawObjectUtils {
    public static Date dateFromString(String format, String date) {
        if (date == null) return null;
        try {
            return new SimpleDateFormat(format).parse(date);
        }catch (ParseException e) {
            return null;
        }
    }
}
