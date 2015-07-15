package com.altfatterz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.net.URI;
import java.net.URISyntaxException;

public abstract class RedisConfig {
}

@Configuration
@Profile("dev")
class DevRedisConfig extends RedisConfig {

    @Bean
    public JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

}

@Configuration
@Profile("heroku")
class HerokuRedisConfig extends RedisConfig {

    @Value("${spring.redis.uri}")
    private String redisUri;

    @Bean
    public JedisConnectionFactory connectionFactory() throws URISyntaxException {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();

        URI uri = new URI(redisUri);

        connectionFactory.setHostName(uri.getHost());
        connectionFactory.setPort(uri.getPort());
        connectionFactory.setPassword(uri.getUserInfo().split(":", 2)[1]);

        return connectionFactory;
    }

}
