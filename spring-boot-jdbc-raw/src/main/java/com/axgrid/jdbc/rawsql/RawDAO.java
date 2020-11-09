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

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    @interface RawInsert {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.SOURCE)
    @interface RawQuery {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
        boolean nullIfObjectEmpty() default true;
        Class<RowMapper<?>> mapper();
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    @interface RawUpdate {
        @AliasFor("query")
        String value() default "";
        @AliasFor("value")
        String query() default "";
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    @interface RawParam {
        @AliasFor("name")
        String value() default "";
        @AliasFor("value")
        String name() default "";
    }

    @Target(ElementType.PARAMETER)
    @Retention(RetentionPolicy.SOURCE)
    @interface RawParamObject {
    }
}
