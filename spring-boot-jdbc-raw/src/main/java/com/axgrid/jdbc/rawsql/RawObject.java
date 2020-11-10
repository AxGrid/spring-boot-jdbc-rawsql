package com.axgrid.jdbc.rawsql;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface RawObject {
    boolean onlyIncludedFields() default false;
    String[] includeFields() default {};
    String[] excludedFields() default {};
    boolean useFieldsOnly() default false;

    @Target({ElementType.FIELD, ElementType.METHOD})
    //@Retention(RetentionPolicy.SOURCE)
    @interface Include {
        @AliasFor("fieldName")
        String value() default "";
        @AliasFor("value")
        String fieldName() default "";
    }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface Exclude { }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface JsonObject { }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface EnumToInteger { }

    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface EnumToOrdinal { }


    @Target({ElementType.FIELD, ElementType.METHOD})
    @interface Processor{
        @AliasFor("name")
        String value() default "";
        @AliasFor("value")
        String name() default "";
    }

}
