package com.axgrid.jdbc.rawsql;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public @interface RawResult {

    @Target({ElementType.METHOD})
    @interface JsonObject { }

    @Target({ElementType.METHOD})
    @interface ProtoObject { }

    @Target({ElementType.METHOD})
    @interface StringToEnum {
        String setter() default "valueOf";
    }

    @Target({ElementType.METHOD})
    @interface IntegerToEnum {
        String setter() default "setNumber";
    }

    @Target({ElementType.METHOD})
    @interface OrdinalToEnum { }

    @Target({ElementType.METHOD})
    @interface StringToDate {
        String format() default "yyyy-MM-dd hh:mm:ss";
    }

    @Target({ElementType.METHOD})
    @interface LongToDate { }

    @Target({ElementType.METHOD})
    @interface Processor{
        @AliasFor("name")
        String value() default "";
        @AliasFor("value")
        String name() default "";

        String[] params() default {};
    }
}
