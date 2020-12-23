package com.spring.redis.config;

import io.lettuce.core.ReadFrom;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStaticMasterReplicaConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

/**
 * Created on 23/December/2020 By Author Eresh, Gorantla
 **/
@Configuration
public class RedisConfiguration {

	final RedisProperties properties;

	public RedisConfiguration(RedisProperties properties) {
		this.properties = properties;
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
		                                                                    .readFrom(ReadFrom.REPLICA_PREFERRED)
		                                                                    .build();
		RedisStaticMasterReplicaConfiguration staticMasterReplicaConfiguration = new RedisStaticMasterReplicaConfiguration(properties.getMaster().getHost(),
		                                                                                                                   properties.getMaster().getPort());
		properties.getSlaves().forEach(slave -> staticMasterReplicaConfiguration.addNode(slave.getHost(), slave.getPort()));
		return new LettuceConnectionFactory(staticMasterReplicaConfiguration, clientConfig);
	}

}