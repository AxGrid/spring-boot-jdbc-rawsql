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

@Service
public class {{objectName}}RawDAO implements {{objectName}} {



}
