@Override
    {{~>RawDAOCache this}}
    {{~>RawDAOScheduled this}}
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        {{>RawDAOProperty this}}
    {{~else}}
        {{>RawDAOPropertyMap this}}
    {{~/if}}
    {{~#if void}}jdbcTemplate.update("{{query}}", parameters);
    {{~else}}
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update("{{query}}", parameters, keyHolder);
    {{~#if returnRawObject }}
        {{rawObjectName}}.setId(keyHolder.getKey().longValue()); //TODO: Fix that
        return {{rawObjectName}};
    {{~else}}
        return keyHolder.getKey().{{simpleReturn}}Value();
    {{~/if}}
    {{~/if}}
    };
