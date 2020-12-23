package com.spring.redis.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created on 23/December/2020 By Author Eresh, Gorantla
 **/
@Data
@ConfigurationProperties(prefix = "redis")
@Configuration
public class RedisProperties {
	private String host;
	private int port;
	private RedisProperties master;
	private List<RedisProperties> slaves;
}