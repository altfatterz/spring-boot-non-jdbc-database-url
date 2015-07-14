package com.altfatterz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@SpringBootApplication
@RestController
public class SpringBootNonJdbcDatabaseUrlApplication {

    @Autowired
    DataSource dataSource;

    @RequestMapping("/")
    public Properties index() throws SQLException {
        org.apache.tomcat.jdbc.pool.DataSource pooledDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;

        Properties props = new Properties();

        props.setProperty("name", pooledDataSource.getName());
        props.setProperty("pool-name", pooledDataSource.getPoolName());
        props.setProperty("url", pooledDataSource.getUrl());
        props.setProperty("username", pooledDataSource.getUsername());
        props.setProperty("validation-query", pooledDataSource.getValidationQuery());

        props.setProperty("initial-size", pooledDataSource.getInitialSize() + "");
        props.setProperty("max-active", pooledDataSource.getMaxActive() + "");
        props.setProperty("min-idle", pooledDataSource.getMinIdle() + "");
        props.setProperty("max-idle", pooledDataSource.getMaxIdle() + "");

        return props;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNonJdbcDatabaseUrlApplication.class, args);
    }
}
