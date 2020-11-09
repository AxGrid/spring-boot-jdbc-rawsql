@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        SqlParameterSource parameters = new BeanPropertySqlParameterSource({{rawObjectName}});
    {{~else}}
        Map parameters = new HashMap();
        {{~#each parameters}}parameters.put("{{name}}", {{name}});
        {{~/each}}
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
