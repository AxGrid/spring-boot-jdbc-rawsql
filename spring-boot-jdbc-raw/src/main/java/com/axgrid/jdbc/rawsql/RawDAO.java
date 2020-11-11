package com.axgrid.jdbc.rawsql;

import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface RawDAO {

    @Target(ElementType.METHOD)
    @interface RawInsert {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
    }

    @Target(ElementType.METHOD)
    @interface RawQuery {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
        boolean nullIfObjectEmpty() default true;
        Class<RowMapper<?>>[] mapper() default {};
    }

    @Target(ElementType.METHOD)
    @interface RawUpdate {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
    }

//    @Target(ElementType.PARAMETER)
//    @interface RawParamObject {
//    }
}
