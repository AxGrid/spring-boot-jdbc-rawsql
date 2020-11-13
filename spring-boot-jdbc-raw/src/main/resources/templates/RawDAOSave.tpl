@Override
        {{~>RawDAOCache this}}
        {{~>RawDAOScheduled this}}
        public {{returnType}} {{name}}({{flatParameters}}) {
            if ({{rawObjectName}}.{{id}}() == null) {
                {{~#if void~}}
                {{create}}({{rawObjectName}});
                {{~else~}}
                return {{create}}({{rawObjectName}});
                {{~/if~}}
            } else {
                {{~#if void~}}
                    {{update}}({{rawObjectName}});
                {{~else~}}
                return {{update}}({{rawObjectName}});
                {{~/if~}}
            }
        }
