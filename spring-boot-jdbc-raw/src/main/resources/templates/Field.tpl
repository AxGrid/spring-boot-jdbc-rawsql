{{#if setter}}res.{{methodName}}(resultSet{{fromResultSet}});
    {{else}}res.{{name}} = resultSet{{fromResultSet}};{{/if}}
