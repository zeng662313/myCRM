package com.yumoxuan.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    @Value("${spring.redis.redisson.address}")
    public String address;
    @Value("${spring.redis.redisson.database}")
    public Integer database;

    @Bean
    public RedissonClient getRedissonClient(){
        Config config=new Config();
        config.useSingleServer().setAddress(address).setDatabase(database);
       return Redisson.create(config);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurePrometheus(@Value("${spring.application.name}") String name){
        return (registry -> registry.config().commonTags("application",name));
    }
}
