var parameters = new BeanPropertySqlParameterSource({{rawObjectName}}) {

            @Override
            public int getSqlType(String paramName) {
                {{~#each processorFields}}
                if (paramName.equals("{{name}}")) {
                {{~#equals valueProcessor 'json'}}
                    return java.sql.Types.VARCHAR;
                {{~/equals}}
                {{~#equals valueProcessor 'proto'}}
                    return java.sql.Types.BLOB;
                {{~/equals}}
                {{~#equals valueProcessor 'enumToString'}}
                    return java.sql.Types.VARCHAR;
                {{~/equals}}
                {{~#equals valueProcessor 'dateToString'}}
                    return java.sql.Types.VARCHAR;
                {{~/equals}}
                {{~#equals valueProcessor 'enumToInt'}}
                    return java.sql.Types.INTEGER;
                {{~/equals}}
                {{~#equals valueProcessor 'enumToOrdinal'}}
                    return java.sql.Types.INTEGER;
                {{~/equals}}
                {{~#equals valueProcessor 'dateToLong'}}
                    return java.sql.Types.BIGINT;
                {{~/equals}}
                }
                {{~/each}}

                return super.getSqlType(paramName);
            }



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
                {{~#equals valueProcessor 'proto'}}
                    value = (({{type}})value).toByteArray();
                {{/equals}}
                {{~#equals valueProcessor 'enumToString'}}
                    value = value.toString();
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

                if (value instanceof java.util.Date) {
                    value = new java.sql.Time(((java.util.Date)value).getTime());
                }

                if (value instanceof Enum) {
                    value = value.toString();
                }

                if (log.isTraceEnabled()) log.trace("Process field {} = {} ({})", paramName, value, value.getClass().getName());
                return value;
            }
        };
