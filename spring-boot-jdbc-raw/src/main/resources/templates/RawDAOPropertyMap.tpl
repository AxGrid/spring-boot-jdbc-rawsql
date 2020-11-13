Map parameters = new HashMap();
        {{~#each parameters~}}
        {{~#if valueProcessor~}}
        {{~#equals valueProcessor 'json'~}}
        parameters.put("{{name}}", RawObjectUtils.toJson({{name}}));
        {{~/equals~}}

        {{~#equals valueProcessor 'proto'}}
        parameters.put("{{name}}", {{name}} == null ? null : {{name}}{{valueProcessorArguments.[0]}}.toByteArray());
        {{/equals}}
        {{~#equals valueProcessor 'enumToString'}}
        parameters.put("{{name}}", {{name}}.{{valueProcessorArguments.[0]}}());
        {{/equals}}
        {{~#equals valueProcessor 'enumToInt'}}
        parameters.put("{{name}}", {{name}}.{{valueProcessorArguments.[0]}}());
        {{/equals}}
        {{~#equals valueProcessor 'enumToOrdinal'}}
        parameters.put("{{name}}", {{name}}.ordinal());
        {{/equals}}
        {{~#equals valueProcessor 'dateToLong'}}
        parameters.put("{{name}}", {{name}} == null ? null : {{type}}.getTime());
        {{/equals}}
        {{~#equals valueProcessor 'dateToString'}}
        parameters.put("{{name}}", {{name}} == null ? null : new SimpleDateFormat("{{valueProcessorArguments.[0]}}").format({{name}}));
        {{/equals}}
        {{~else~}}
        parameters.put("{{name}}", {{name}});
        {{~/if~}}
        {{~/each~}}
