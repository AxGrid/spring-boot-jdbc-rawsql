{{~#if resultProcessor~}}
        new RowMapper<{{returnType}}>() {
            {{~#equals resultProcessor 'json' }}
            final ObjectMapper rowJsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            {{~/equals}}

            @Override
            public {{returnType}} mapRow(ResultSet resultSet, int i) throws SQLException {

            {{~#equals resultProcessor 'enumToString' ~}}
                return {{returnType}}.valueOf(resultSet.getString(1));
            {{~/equals~}}
            {{~#equals resultProcessor 'enumToInt' ~}}
                return {{returnType}}.{{resultProcessorArguments.[0]}}(resultSet.getInt(1));
            {{~/equals~}}
            {{~#equals resultProcessor 'enumToOrdinal' ~}}
                return {{returnType}}.values()[resultSet.getInt(1)];
            {{~/equals~}}
            {{~#equals resultProcessor 'dateToString' ~}}
                return RawObjectUtils.dateFromString("{{resultProcessorArguments.[0]}}", resultSet.getString(1));
            {{~/equals~}}
            {{~#equals resultProcessor 'dateToLong' ~}}
                return RawObjectUtils.executeOrNull(resultSet.getObject(1, Long.class), (v) => new Date(v));
            {{~/equals~}}
            {{~#equals resultProcessor 'json' ~}}
                return RawObjectUtils.executeOrNull(resultSet.getString(1), (v) -> {
                    try{
                        return rowJsonMapper.readValue(v, {{returnType}}.class);
                    }catch(JsonProcessingException e) {
                        if (log.isWarnEnabled()) log.warn("RawSQL json exception.", e);
                        return null;
                    }
                });
            {{~/equals~}}

            {{~#equals resultProcessor 'proto' ~}}
                return RawObjectUtils.executeOrNull(resultSet.getBytes(1), (v) -> {
                    try{
                        return {{returnType}}.parseFrom(new byte[0])
                    }catch(InvalidProtocolBufferException e) {
                        if (log.isWarnEnabled()) log.warn("RawSQL proto exception.", e);
                        return null;
                    }
                });
            {{~/equals~}}

            }
        }
{{~else~}}{{mapper}}{{~/if}}


