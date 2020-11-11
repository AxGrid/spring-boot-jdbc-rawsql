{{~#if valueProcessor ~}}
{{~#equals valueProcessor 'enumToString' ~}}
{{type}}.{{valueProcessorArguments.[1]}}(resultSet.getString("{{fieldName}}"))
{{~/equals}}
{{~#equals valueProcessor 'enumToOrdinal' ~}}
{{type}}.values()[resultSet.getInt("{{fieldName}}")]
{{~/equals}}
{{~#equals valueProcessor 'enumToInt' ~}}
{{type}}.{{valueProcessorArguments.[1]}}(resultSet.getInt("{{fieldName}}"))
{{~/equals}}
{{~#equals valueProcessor 'dateToString' ~}}
RawObjectUtils.dateFromString("{{valueProcessorArguments.[0]}}", resultSet.getString("{{fieldName}}"))
{{~/equals}}
{{~#equals valueProcessor 'dateToLong' ~}}
new Date(resultSet.getLong("{{fieldName}}"))
{{~/equals}}
{{~else~}}
resultSet{{fromResultSet}}
{{~/if~}}
