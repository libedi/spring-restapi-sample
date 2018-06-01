package com.libedi.demo.framework.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import lombok.RequiredArgsConstructor;

/**
 * ApplicationStartupListener
 * - application 로딩시 등록된 handlerMapping 정보 조회
 * @author Sangjun, Park
 *
 */
@Component
@RequiredArgsConstructor
public class ApplicationStartupListener {
	
	private final Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);
	
	private final RequestMappingHandlerMapping handlerMapping;
	
//	@Autowired
//	public ApplicationStartupListener(final RequestMappingHandlerMapping handlerMapping) {
//		this.handlerMapping = handlerMapping;
//	}

	@EventListener
	public void handleContextRefreshedEvent(final ContextRefreshedEvent event) {
		handlerMapping.getHandlerMethods().keySet().forEach(key -> key.getPatternsCondition().getPatterns().forEach(logger::info));
	}

}
