package com.axgrid.jdbc.rawsql;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

public class TestAnnotationsCollectAllFields {

    MyTestAnnotation a;
    MyTestAnnotation a2;

    @Target({ElementType.TYPE, ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyTestAnnotation {
        @AliasFor("cacheNames")
        String[] value() default {};

        @AliasFor("value")
        String[] cacheNames() default {};

        String key() default "";

        String keyGenerator() default "";

        String cacheManager() default "";

        String cacheResolver() default "";

        String condition() default "";

        String unless() default "";

        boolean sync() default false;
    }

    @MyTestAnnotation(cacheNames = "hello", key = "#key", sync = true)
    public final class TestClass {

    }

    @MyTestAnnotation({"hello", "word"})
    public final class Test2Class {

    }

    @Before
    public void createCollection() {
         var t = new TestClass();
         a = t.getClass().getAnnotation(MyTestAnnotation.class);
         a2 = Test2Class.class.getAnnotation(MyTestAnnotation.class);
    }

    @Test
    public void testACollect() throws Exception {
        var res = RawUtils.collectAllAnnotationParams(a);
        System.out.println(res);
        Assert.assertTrue(res.contains("cacheNames = \"hello\""));
        Assert.assertTrue(res.contains("key = \"#key\""));
        Assert.assertTrue(res.contains("sync = true"));
    }

    @Test
    public void testA2Collect() throws Exception {
        var res = RawUtils.collectAllAnnotationParams(a2);
        System.out.println(res);
    }

    @Test
    public void testTimeStamp() throws Exception {
        System.out.printf("%d\n", new Date().getTime());
    }

}
