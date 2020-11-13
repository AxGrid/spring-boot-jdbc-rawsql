package com.axgrid.jdbc.rawsql;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;
import java.util.logging.Logger;


public final class RawObjectUtils {
    final static ObjectMapper om = new ObjectMapper()
            .configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);

    final static Logger logger = Logger.getLogger("RawSQL");

    public static Date dateFromString(String format, String date) {
        if (date == null) return null;
        try {
            return new SimpleDateFormat(format).parse(date);
        }catch (ParseException e) {
            return null;
        }
    }

    public static String toJson(Object o) {
        try {
            om.readValue("", String.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            if (o == null) return null;
            return om.writeValueAsString(o);
        }catch (JsonProcessingException e) {
            logger.warning("RawSQL error processing json object. " + e.getMessage());
            return null;
        }
    }

    public static <T, R> R executeOrNull(T value, Function<T, R> func) {
        if (value == null) return null;
        return func.apply(value);
    }

    public static <T, R> R nullIfException(T value, Function<T, R> func) {
        if (value == null) return null;
        try {
            return func.apply(value);
        }catch (Exception e) {
            return null;
        }
    }

}
