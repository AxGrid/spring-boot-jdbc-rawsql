package com.axgrid.jdbc.rawsql;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
public @interface RawSpring {

    @Target({ElementType.METHOD})
    @interface Scheduled {
        String CRON_DISABLED = "-";

        String cron() default "";

        String zone() default "";

        long fixedDelay() default -1L;

        String fixedDelayString() default "";

        long fixedRate() default -1L;

        String fixedRateString() default "";

        long initialDelay() default -1L;

        String initialDelayString() default "";
    }


    @Target({ElementType.TYPE, ElementType.METHOD})
    @interface Cacheable {
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

    @Target({ElementType.TYPE, ElementType.METHOD})
    @interface CachePut {
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
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @interface CacheEvict {
        @AliasFor("cacheNames")
        String[] value() default {};

        @AliasFor("value")
        String[] cacheNames() default {};

        String key() default "";

        String keyGenerator() default "";

        String cacheManager() default "";

        String cacheResolver() default "";

        String condition() default "";

        boolean allEntries() default false;

        boolean beforeInvocation() default false;
    }

    @Target({ElementType.TYPE, ElementType.METHOD})
    @interface Caching {
        Cacheable[] cacheable() default {};

        CachePut[] put() default {};

        CacheEvict[] evict() default {};
    }

}
