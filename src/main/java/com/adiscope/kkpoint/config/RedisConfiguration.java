package com.adiscope.kkpoint.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Created by 최경근(keun0912@neowiz.com) on 2020-03-09.
 */
@EnableCaching
@Configuration
public class RedisConfiguration {

    @Primary
    @Bean(name = "cacheManager")
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory);
        RedisCacheConfiguration configuration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))
                        .entryTtl(Duration.ofMinutes(5L));
        builder.cacheDefaults(configuration);
        return builder.build();
    }

}
