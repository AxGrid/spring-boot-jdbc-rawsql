{{~#if valueProcessor ~}}
{{~#equals valueProcessor 'enumToOrdinal' ~}}
{{type}}.values()[resultSet.getInt("{{fieldName}}")]
{{~/equals}}
{{~else~}}
resultSet{{fromResultSet}}
{{~/if~}}
