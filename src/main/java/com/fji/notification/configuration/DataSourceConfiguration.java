package com.fji.notification.configuration;

import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@NoArgsConstructor
public class DataSourceConfiguration {

    @Bean
    public JdbcTemplate serviceJdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
