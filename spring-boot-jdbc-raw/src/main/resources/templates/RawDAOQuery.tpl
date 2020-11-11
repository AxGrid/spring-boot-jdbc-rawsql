@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        {{>RawDAOProperty this}}
    {{~else}}
        {{>RawDAOPropertyMap this}}
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
