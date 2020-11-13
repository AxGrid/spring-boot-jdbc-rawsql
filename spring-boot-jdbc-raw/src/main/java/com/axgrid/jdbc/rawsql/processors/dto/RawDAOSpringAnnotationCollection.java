package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawSpring;
import com.axgrid.jdbc.rawsql.RawUtils;

import javax.lang.model.element.Element;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RawDAOSpringAnnotationCollection {

    RawSpring.Cacheable cacheable;
    RawSpring.CacheEvict cacheEvict;
    RawSpring.CachePut cachePut;
    RawSpring.Caching caching;
    RawSpring.Scheduled scheduled;

    public boolean isCache() {
        return cacheable != null || cacheEvict != null || cachePut != null || caching != null;
    }

    public boolean isScheduled() {
        return scheduled != null;
    }


    public String getParams(Object annotation) {
        return RawUtils.collectAllAnnotationParams(annotation);
    }

    public String getScheduledParams() { return getParams(scheduled); }
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

    public RawSpring.Cacheable getCacheable() {
        return cacheable;
    }

    public void setCacheable(RawSpring.Cacheable cacheable) {
        this.cacheable = cacheable;
    }

    public RawSpring.CacheEvict getCacheEvict() {
        return cacheEvict;
    }

    public void setCacheEvict(RawSpring.CacheEvict cacheEvict) {
        this.cacheEvict = cacheEvict;
    }

    public RawSpring.CachePut getCachePut() {
        return cachePut;
    }

    public void setCachePut(RawSpring.CachePut cachePut) {
        this.cachePut = cachePut;
    }

    public RawSpring.Caching getCaching() {
        return caching;
    }

    public void setCaching(RawSpring.Caching caching) {
        this.caching = caching;
    }

    public RawDAOSpringAnnotationCollection(Element e) {
        cacheable = e.getAnnotation(RawSpring.Cacheable.class);
        cacheEvict = e.getAnnotation(RawSpring.CacheEvict.class);
        cachePut = e.getAnnotation(RawSpring.CachePut.class);
        caching = e.getAnnotation(RawSpring.Caching.class);
        scheduled = e.getAnnotation(RawSpring.Scheduled.class);
    }

    public RawDAOSpringAnnotationCollection() {}
}
