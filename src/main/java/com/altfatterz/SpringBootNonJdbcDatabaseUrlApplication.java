package com.altfatterz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootApplication
@RestController
public class SpringBootNonJdbcDatabaseUrlApplication {

    @Autowired
    DataSource datasource;

    @RequestMapping("/")
    public String greeting() throws SQLException {
        return datasource.getClass().getName();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNonJdbcDatabaseUrlApplication.class, args);
    }
}
