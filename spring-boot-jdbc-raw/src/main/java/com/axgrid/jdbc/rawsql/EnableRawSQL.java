package com.axgrid.jdbc.rawsql;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({RawSQLConfiguration.class})
public @interface EnableRawSQL {
}
