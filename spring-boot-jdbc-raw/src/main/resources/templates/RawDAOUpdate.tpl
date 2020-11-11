@Override
    public {{returnType}} {{name}}({{flatParameters}}) {
    {{~#if rawObjectName }}
        {{>RawDAOProperty this}}
    {{~else}}
        {{>RawDAOPropertyMap this}}
    {{~/if}}
        jdbcTemplate.update("{{query}}", parameters);
    {{~#if returnRawObject }}
        return {{rawObjectName}};
    {{/if}}
    };
