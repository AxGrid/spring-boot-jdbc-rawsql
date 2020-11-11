Map parameters = new HashMap();
        {{~#each parameters~}}
            parameters.put("{{name}}", {{name}});
        {{~/each~}}
