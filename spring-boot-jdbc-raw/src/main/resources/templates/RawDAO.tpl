{{#if packageName}}package {{packageName}};{{/if}}
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.NotImplementedException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.dao.EmptyResultDataAccessException;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Slf4j
@Service
public class {{objectName}}RawDAO implements {{objectName}} {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    final ObjectMapper om = new ObjectMapper();

    {{#each undefinedMethods}}
    @Override
    public {{returnType}} {{name}}({{flatParameters}}) {
        throw new NotImplementedException("Method {{nane}} Not Implemented");
    };
    {{~/each}}

    {{~#each updateMethods}}
    {{>RawDAOUpdate this }}
    {{~/each}}

    {{~#each insertMethods}}
    {{>RawDAOInsert this }}
    {{~/each}}

    {{~#each queryMethods}}
    {{>RawDAOQuery this }}
    {{~/each}}

}
