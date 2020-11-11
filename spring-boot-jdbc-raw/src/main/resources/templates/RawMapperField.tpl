{{~#if setter~}}
        res.{{methodName}}(resultSet{{fromResultSet}});
{{~else~}}
{{~#if field~}}
        res.{{name}} = resultSet{{fromResultSet}};
{{~/if~}}
{{~/if~}}

