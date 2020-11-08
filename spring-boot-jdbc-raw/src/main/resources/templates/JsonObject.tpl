try{
{{#if setter}}res.{{methodName}}(om.readValue(resultSet.getString("{{fieldName}}"), {{objectType}}.class));
{{else}}res.{{name}} = om.readValue(resultSet.getString("{{fieldName}}"), {{objectType}}.class);{{/if}}
}catch (JsonProcessingException e) {
{{#if setter}}res.{{methodName}}({{default}});
{{else}}res.{{name}} = {{default}};{{/if}}
}
