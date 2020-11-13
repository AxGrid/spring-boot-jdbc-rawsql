package com.axgrid.jdbc.rawsql.processors.dto;

import com.axgrid.jdbc.rawsql.RawCache;

import javax.lang.model.element.Element;

public class RawCacheAnnotationCollection {

    RawCache.Cacheable cacheable;
    RawCache.CacheEvict cacheEvict;
    RawCache.CachePut cachePut;
    RawCache.Caching caching;

    public RawCacheAnnotationCollection(Element e) {
        cacheable = e.getAnnotation(RawCache.Cacheable.class);
        cacheEvict = e.getAnnotation(RawCache.CacheEvict.class);
        cachePut = e.getAnnotation(RawCache.CachePut.class);
        caching = e.getAnnotation(RawCache.Caching.class);
    }
}
