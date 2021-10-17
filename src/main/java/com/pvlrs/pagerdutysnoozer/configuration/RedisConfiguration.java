package com.pvlrs.pagerdutysnoozer.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

@Getter
@Setter
@ConfigurationProperties(prefix = "com.pvlrs.redis")
@Configuration
public class RedisConfiguration {

    private String host = "localhost";
    private Integer port = 6379;
    private String password;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        Optional.ofNullable(password)
                .filter(StringUtils::isNotBlank)
                .ifPresent(config::setPassword);
        return new JedisConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        final RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
//        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
        return template;
    }
}
