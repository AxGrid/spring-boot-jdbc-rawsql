@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        SqlParameterSource parameters = new BeanPropertySqlParameterSource({{rawObjectName}});
    {{~else}}
        Map parameters = new HashMap();
        {{~#each parameters}}parameters.put("{{name}}", {{name}});
        {{~/each}}
    {{~/if}}
    {{~#if list }}
        return jdbcTemplate.query("{{query}}", parameters, {{mapper}});
    {{~else}}
        {{~#if nullIfObjectEmpty}}
        try{
            return jdbcTemplate.queryForObject("{{query}}", parameters, {{mapper}});
        }catch(EmptyResultDataAccessException ignore){
            return {{null}};
        }
        {{~else}}
        return jdbcTemplate.queryForObject("{{query}}", parameters, {{mapper}});
        {{~/if}}
    {{~/if}}
    }
