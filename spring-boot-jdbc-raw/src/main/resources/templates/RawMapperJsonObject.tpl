try{
        {{~#if setter~}}
            String str__{{fieldName}} = resultSet.getString("{{fieldName}}");
            res.{{methodName}}(str__{{fieldName}} == null ? null : om.readValue(str__{{fieldName}}, {{objectType}}.class));
        {{~else~}}
            String str__{{fieldName}} = resultSet.getString("{{fieldName}}");
            res.{{name}} = str{__{fieldName}} == null ? null : om.readValue(str__{{fieldName}}, {{objectType}}.class);
        {{~/if~}}
        }catch (JsonProcessingException e) {
        {{~#if setter~}}
            res.{{methodName}}({{default}});
        {{~else~}}
            res.{{name}} = {{default}};
        {{~/if~}}
        }
