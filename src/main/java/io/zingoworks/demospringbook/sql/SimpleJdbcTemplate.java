package io.zingoworks.demospringbook.sql;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SimpleJdbcTemplate extends JdbcTemplate {

    public SimpleJdbcTemplate(DataSource dataSource) {
        super(dataSource);
    }

    public int queryForInt(String sql) {
        return queryForObject(sql, Integer.class);
    }
}
