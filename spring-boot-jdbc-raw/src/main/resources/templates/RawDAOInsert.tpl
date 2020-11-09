@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        var parameters = new BeanPropertySqlParameterSource({{rawObjectName}}) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object value = super.getValue(paramName);
                if (value instanceof Enum) {
                    return value.toString();
                }
                log.info("ADD PARAM {} is {}", paramName, value);
                return value;
            }
        };
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
