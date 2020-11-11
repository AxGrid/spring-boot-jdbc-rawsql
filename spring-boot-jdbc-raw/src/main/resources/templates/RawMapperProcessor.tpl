{{~#if valueProcessor ~}}
{{~#equals valueProcessor 'enumToString' ~}}
{{type}}.valuesOf(resultSet.getString("{{fieldName}}"))
{{~/equals}}
{{~#equals valueProcessor 'enumToOrdinal' ~}}
{{type}}.values()[resultSet.getInt("{{fieldName}}")]
{{~/equals}}
{{~#equals valueProcessor 'enumToInt' ~}}
{{valueProcessorArguments.[1]}}(resultSet.getInt("{{fieldName}}"))
{{~/equals}}
{{~#equals valueProcessor 'dateToString' ~}}
RawObjectUtils.dateFromString("{{valueProcessorArguments.[0]}}", resultSet.getString("{{fieldName}}"))
{{~/equals}}
{{~else~}}
resultSet{{fromResultSet}}
{{~/if~}}
