@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        {{>RawDAOProperty this}}
    {{~else}}
        {{>RawDAOPropertyMap this}}
    {{~/if}}
    {{~#if list }}
        return jdbcTemplate.query("{{query}}", parameters,
            {{>RawDAOQueryProcessor this}}
        );
    {{~else}}
        {{~#if nullIfObjectEmpty}}
        try{
            return jdbcTemplate.queryForObject("{{query}}", parameters,
                {{>RawDAOQueryProcessor this}}
            );
        }catch(EmptyResultDataAccessException ignore){
            return {{null}};
        }
        {{~else}}
        return jdbcTemplate.queryForObject("{{query}}", parameters,
            {{>RawDAOQueryProcessor this}}
        );
        {{~/if}}
    {{~/if}}
    }
