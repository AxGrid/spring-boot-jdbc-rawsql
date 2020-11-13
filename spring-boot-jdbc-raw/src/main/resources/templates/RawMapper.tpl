{{#if packageName}}package {{packageName}};{{/if}}
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.axgrid.jdbc.rawsql.RawObjectUtils;
{{#if fields.jsonObjects}}
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
{{/if}}


public class {{className}} implements RowMapper<{{objectName}}> {
{{#if fields.jsonObjects}}
    final ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
{{/if}}
    @Override
    public {{objectName}} mapRow(ResultSet resultSet, int i) throws SQLException {
        {{objectName}} res = new {{objectName}}();
        {{~#each nonExcludeFields}}
        {{~#equals valueProcessor 'proto' ~}}
        {{>RawMapperProtoObject this ~}}
        {{~else~}}
        {{~#if jsonObject}}
        {{>RawMapperJsonObject this ~}}
        {{~else}}
        {{>RawMapperField this ~}}
        {{~/if}}
        {{~/equals}}
        {{~/each}}
        return res;
    }
}
