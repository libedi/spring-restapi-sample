package com.libedi.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableCaching
public class DemoRestapiApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(DemoRestapiApplication.class, args);
	}
	
	@Component
	static class CacheManagerCheck implements CommandLineRunner {
		private final Logger logger = LoggerFactory.getLogger(CacheManagerCheck.class);
		private final CacheManager cacheManager;
		
		@Autowired
		public CacheManagerCheck(CacheManager cacheManager) {
			this.cacheManager = cacheManager;
		}

		@Override
		public void run(String... args) throws Exception {
			logger.info("Using cacheManager: {}", this.cacheManager.getClass().getName());
		}
	}
	
}
