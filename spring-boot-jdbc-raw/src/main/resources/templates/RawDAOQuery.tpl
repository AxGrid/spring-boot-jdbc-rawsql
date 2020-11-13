@Override
    {{~>RawDAOCache this}}
    {{~>RawDAOScheduled this}}
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
        {{#if optional}}
        try{
            return Optional.of(jdbcTemplate.queryForObject("{{query}}", parameters,
                {{>RawDAOQueryProcessor this}}
                ));
        }catch(EmptyResultDataAccessException ignore){
            return Optional.empty();
        }
        {{~else}}
        try{
            return jdbcTemplate.queryForObject("{{query}}", parameters,
                {{>RawDAOQueryProcessor this}}
            );
        }catch(EmptyResultDataAccessException ignore){
            return {{null}};
        }
        {{/if}}
        {{~else}}
        return jdbcTemplate.queryForObject("{{query}}", parameters,
            {{>RawDAOQueryProcessor this}}
        );
        {{~/if}}
    {{~/if}}
    }
