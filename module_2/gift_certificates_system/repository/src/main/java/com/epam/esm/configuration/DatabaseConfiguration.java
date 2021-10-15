package com.epam.esm.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * Main ESM application configuration.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.epam.esm")
@PropertySource("classpath:database_configuration.properties")
public class DatabaseConfiguration implements WebMvcConfigurer {
    private final Environment environment;

    /**
     * Initialization environment object to gain access to property file.
     *
     * @param environment - Environment object.
     * @see org.springframework.core.env.Environment
     */
    @Autowired
    public DatabaseConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * Created connection pool with HikariCP and set attributes for MySQL database connection.
     *
     * @return - DataSource (Hikari data source)
     * @see com.zaxxer.hikari.HikariConfig
     * @see com.zaxxer.hikari.HikariDataSource
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName(environment.getProperty("driver.classname"));
        hikariConfig.setJdbcUrl(environment.getProperty("jdbc.url"));
        hikariConfig.setUsername(environment.getProperty("db.username"));
        hikariConfig.setPassword(environment.getProperty("db.password"));

        hikariConfig.addDataSourceProperty("cachePrepStmts", environment.getProperty("cache.prep.stmts"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", environment.getProperty("prep.stmt.cache.size"));
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", environment.getProperty("prep.stmt.cache.limit"));

        return new HikariDataSource(hikariConfig);
    }

    /**
     * This is the central class in the JDBC core package. It simplifies the use of JDBC and helps to avoid common errors.
     * It executes core JDBC workflow, leaving application code to provide SQL and extract results.
     * This class executes SQL queries or updates, initiating iteration over ResultSets
     * and catching JDBC exceptions and translating them to the generic,
     * more informative exception hierarchy defined in the org.springframework.dao package.
     *
     * @return - Jdbc template.
     * @see org.springframework.jdbc.core.JdbcTemplate
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
