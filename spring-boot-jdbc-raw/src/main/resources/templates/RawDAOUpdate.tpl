@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
        {{~#if rawObjectName }}
        SqlParameterSource parameters = new BeanPropertySqlParameterSource({{rawObjectName}});
        {{~else}}
        Map parameters = new HashMap();
        {{~#each parameters}}parameters.put("{{name}}", {{name}});
        {{~/each}}
        {{~/if}}
        jdbcTemplate.update("{{query}}", parameters);
        {{~#if returnRawObject }}
        return {{rawObjectName}};
        {{/if}}
    };
