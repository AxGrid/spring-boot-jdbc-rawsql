{{~#if setter~}}
        res.{{methodName}}({{>RawMapperProcessor}});
{{~else~}}
{{~#if field~}}
        res.{{name}} = {{>RawMapperProcessor}};
{{~/if~}}
{{~/if~}}

