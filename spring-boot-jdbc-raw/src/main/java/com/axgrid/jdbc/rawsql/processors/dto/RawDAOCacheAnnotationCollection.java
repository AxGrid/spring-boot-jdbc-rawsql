package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawCache;
import com.axgrid.jdbc.rawsql.RawUtils;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RawDAOCacheAnnotationCollection {

    RawCache.Cacheable cacheable;
    RawCache.CacheEvict cacheEvict;
    RawCache.CachePut cachePut;
    RawCache.Caching caching;

    public boolean isCache() {
        return cacheable != null || cacheEvict != null || cachePut != null || caching != null;
    }

    public String getParams(Object annotation) {
//        if (annotation instanceof RawCache.Cacheable)
//            return RawUtils.arrayOrSingleString(((RawCache.Cacheable)annotation).value());
//
//        if (annotation instanceof RawCache.CacheEvict)
//            return RawUtils.arrayOrSingleString(((RawCache.CacheEvict)annotation).value());
//
//        if (annotation instanceof RawCache.CachePut)
//            return RawUtils.arrayOrSingleString(((RawCache.CachePut)annotation).value());

        return RawUtils.collectAllAnnotationParams(annotation);
    }

    public String getCacheableParams() { return getParams(cacheable); }
    public String getCacheEvictParams() { return getParams(cacheEvict); }
    public String getCachePutParams() { return getParams(cachePut); }

    public String getCachingParams() {
        List<String> resStrings = new ArrayList<>();
        resStrings.add(caching.cacheable().length == 0 ?
                null :
                "cacheable = {" + Arrays.stream(caching.cacheable()).map(item -> String.format("@org.springframework.cache.annotation.Cacheable(%s)", getParams(item))).collect(Collectors.joining(",\n")) + "}");
        resStrings.add(caching.put().length == 0 ?
                null :
                "put = {" + Arrays.stream(caching.put()).map(item -> String.format("@org.springframework.cache.annotation.CachePut(%s)", getParams(item))).collect(Collectors.joining(",\n")) + "}");
        resStrings.add(caching.evict().length == 0 ?
                null :
                "evict = {" + Arrays.stream(caching.evict()).map(item -> String.format("@org.springframework.cache.annotation.CacheEvict(%s)", getParams(item))).collect(Collectors.joining(",\n")) + "}");

        return resStrings.stream().filter(Objects::nonNull).collect(Collectors.joining(",\n"));
    }

    public RawCache.Cacheable getCacheable() {
        return cacheable;
    }

    public void setCacheable(RawCache.Cacheable cacheable) {
        this.cacheable = cacheable;
    }

    public RawCache.CacheEvict getCacheEvict() {
        return cacheEvict;
    }

    public void setCacheEvict(RawCache.CacheEvict cacheEvict) {
        this.cacheEvict = cacheEvict;
    }

    public RawCache.CachePut getCachePut() {
        return cachePut;
    }

    public void setCachePut(RawCache.CachePut cachePut) {
        this.cachePut = cachePut;
    }

    public RawCache.Caching getCaching() {
        return caching;
    }

    public void setCaching(RawCache.Caching caching) {
        this.caching = caching;
    }

    public RawDAOCacheAnnotationCollection(Element e) {
        cacheable = e.getAnnotation(RawCache.Cacheable.class);
        cacheEvict = e.getAnnotation(RawCache.CacheEvict.class);
        cachePut = e.getAnnotation(RawCache.CachePut.class);
        caching = e.getAnnotation(RawCache.Caching.class);
    }

    public RawDAOCacheAnnotationCollection() {}
}
