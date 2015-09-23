package com.altfatterz;

import com.mongodb.Mongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class SpringBootNonJdbcDatabaseUrlApplication {

    @Autowired
    DataSource dataSource;

    @Autowired
    JedisConnectionFactory redisConnectionFactory;

    @Autowired
    Mongo mongo;

    @RequestMapping("/")
    public Map<String, Map<String, String>> index() throws SQLException {
        org.apache.tomcat.jdbc.pool.DataSource pooledDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;

        Map<String, Map<String, String>> result = new HashMap<>();

        Map<String, String> datasource = new HashMap<>();

        datasource.put("name", pooledDataSource.getName());
        datasource.put("pool-name", pooledDataSource.getPoolName());
        datasource.put("url", pooledDataSource.getUrl());
        datasource.put("username", pooledDataSource.getUsername());
        datasource.put("validation-query", pooledDataSource.getValidationQuery());
        datasource.put("initial-size", pooledDataSource.getInitialSize() + "");
        datasource.put("max-active", pooledDataSource.getMaxActive() + "");
        datasource.put("min-idle", pooledDataSource.getMinIdle() + "");
        datasource.put("max-idle", pooledDataSource.getMaxIdle() + "");

        result.put("datasource", datasource);

        Map<String, String> redis = new HashMap<>();
        redis.put("hostname", redisConnectionFactory.getHostName());
        redis.put("port", redisConnectionFactory.getPort() + "");

        result.put("redis", redis);

        Map<String, String> mongoConnection = new HashMap<>();
        mongoConnection.put("host", mongo.getAddress().getHost());
        mongoConnection.put("port", mongo.getAddress().getPort() + "");

        result.put("mongo", mongoConnection);

        result.put("test", Collections.singletonMap("foo", "bar"));
        return result;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootNonJdbcDatabaseUrlApplication.class, args);
    }
}
