package com.libedi.demo.framework.config;

import javax.cache.configuration.MutableConfiguration;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache Configuration
 * @author Sangjun, Park
 *
 */
@Configuration
public class CacheConfig {

	/**
	 * JCacheManagerCustomizer
	 * @return
	 */
	@Bean
	public JCacheManagerCustomizer jCacheManagerCustomizer() {
		return (cacheManager -> {
			cacheManager.createCache("customer", new MutableConfiguration<>()
					/*
					 * ExpiryPolicy
					 * - AccessedExpiryPolicy	: 접근시간을 기준으로 만료시간 산정.
					 * - CreatedExpiryPolicy	: 생성시간을 기준으로 만료시간 산정.
					 * - EternalExpiryPolicy	: 만료 안시킴. evict는 가능. (default)
					 * - ModifiedExpiryPolicy	: 생성/수정시간을 기준으로 만료시간 산정.
					 * - TouchedExpiryPolicy	: 접근/생성/수정시간을 기준으로 만료시간 산정.
					 */
//					.setExpiryPolicyFactory(TouchedExpiryPolicy.factoryOf(Duration.ONE_HOUR))
//					.setStoreByValue(false)
//					.setStatisticsEnabled(false)
					);
		});
	}
}
