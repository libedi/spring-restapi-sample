package com.libedi.demo.framework.handler;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CacheEventListener
 * @author Sangjun, Park
 *
 */
public class RestCacheEventLogger implements CacheEventListener<String, Object> {
	private final Logger logger = LoggerFactory.getLogger(RestCacheEventLogger.class);

	@Override
	public void onEvent(CacheEvent<? extends String, ? extends Object> event) {
		logger.info("event: {}, key: {}, old value: {}, new value: {}", event.getType(), event.getKey(), event.getOldValue(), event.getNewValue());
	}

}
