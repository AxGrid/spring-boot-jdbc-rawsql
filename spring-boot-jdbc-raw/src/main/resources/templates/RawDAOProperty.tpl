var parameters = new BeanPropertySqlParameterSource({{rawObjectName}}) {
            @Override
            public Object getValue(String paramName) throws IllegalArgumentException {
                Object value = super.getValue(paramName);
                if (value == null) return value;
                {{#each processorFields}}
                if (paramName.equals("{{name}}")) {
                {{~#equals valueProcessor 'json'}}
                    try {
                        value = om.writeValueAsString(value);
                    }catch (JsonProcessingException e) {
                        log.warn("RawSQL error processing field {{name}}.", e);
                        value = null;
                    }
                {{/equals}}
                {{~#equals valueProcessor 'enumToInt'}}
                    value = (({{type}})value).{{valueProcessorArguments.[0]}}();
                {{/equals}}
                {{~#equals valueProcessor 'enumToOrdinal'}}
                    value = (({{type}})value).ordinal();
                {{/equals}}
                {{~#equals valueProcessor 'dateToLong'}}
                    value = (({{type}})value).getTime();
                {{/equals}}
                {{~#equals valueProcessor 'dateToString'}}
                    value = new SimpleDateFormat("{{valueProcessorArguments.[0]}}").format(({{type}})value);
                {{/equals}}
                }
                {{/each}}

                if (value instanceof Enum) {
                    value = value.toString();
                }

                log.warn("Process field {} = {}", paramName, value);
                return value;
            }
        };
