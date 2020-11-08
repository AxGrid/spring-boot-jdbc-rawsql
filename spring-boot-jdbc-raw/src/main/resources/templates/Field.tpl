{{#if setter}}res.{{methodName}}({{fromResultSet}});
{{else}}res.{{name}} = {{fromResultSet}};{{/if}}
