package com.libedi.demo.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * ApplicationStartupListener
 * - application 로딩시 등록된 handlerMapping 정보 조회
 * @author Sangjun, Park
 *
 */
@Component
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {
	
	private final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
	
	private final RequestMappingHandlerMapping handlerMapping;
	
	@Autowired
	public ApplicationStartupListener(RequestMappingHandlerMapping handlerMapping) {
		this.handlerMapping = handlerMapping;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		handlerMapping.getHandlerMethods().keySet().forEach(key -> key.getPatternsCondition().getPatterns().forEach(logger::info));
	}

}
