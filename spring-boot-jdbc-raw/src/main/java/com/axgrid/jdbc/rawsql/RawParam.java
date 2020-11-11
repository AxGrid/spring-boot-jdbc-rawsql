package com.axgrid.jdbc.rawsql;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
public @interface RawParam {
    @AliasFor("name")
    String value() default "";
    @AliasFor("value")
    String name() default "";

    @Target({ElementType.PARAMETER})
    @interface JsonObject { }

    @Target({ElementType.PARAMETER})
    @interface ProtoObject { }

    @Target({ElementType.PARAMETER})
    @interface EnumToString {
        String getter() default "toString";
        String setter() default "valueOf";
    }

    @Target({ElementType.PARAMETER})
    @interface EnumToInteger {
        String getter() default "getNumber";
        String setter() default "setNumber";
    }

    @Target({ElementType.PARAMETER})
    @interface EnumToOrdinal { }

    @Target({ElementType.PARAMETER})
    @interface DateToString {
        String format() default "yyyy-MM-dd hh:mm:ss";
    }

    @Target({ElementType.PARAMETER})
    @interface DateToLong { }

    @Target({ElementType.PARAMETER})
    @interface Processor{
        @AliasFor("name")
        String value() default "";
        @AliasFor("value")
        String name() default "";

        String[] params() default {};
    }

    @Target(ElementType.PARAMETER)
    @interface RawParamObject {
    }

}
