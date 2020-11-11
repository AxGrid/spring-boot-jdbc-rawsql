package com.axgrid.jdbc.rawsql;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;


public final class RawObjectUtils {
    final static ObjectMapper om = new ObjectMapper();

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
            if (o == null) return null;
            return om.writeValueAsString(o);
        }catch (JsonProcessingException e) {
            logger.warning("RawSQL error processing json object. " + e.getMessage());
            return null;
        }
    }

}
