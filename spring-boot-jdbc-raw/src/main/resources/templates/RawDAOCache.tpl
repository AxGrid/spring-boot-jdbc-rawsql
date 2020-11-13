{{~#if cache}}
    {{~#if cache.cacheable}}
    @org.springframework.cache.annotation.Cacheable({{cache.cacheableParams}})
    {{~/if}}
    {{~#if cache.cacheEvict}}
    @org.springframework.cache.annotation.CacheEvict({{cache.cacheEvictParams}})
    {{~/if}}
    {{~#if cache.cachePut}}
    @org.springframework.cache.annotation.CachePut({{cache.cachePutParams}})
    {{~/if}}
    {{~#if cache.caching}}
    @org.springframework.cache.annotation.Caching(
        {{cache.cachingParams}}
    )
    {{~/if}}
{{~/if~}}
