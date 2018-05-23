package com.libedi.demo.framework.model;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

/**
 * Request-scope bean
 * @author Sangjun, Park
 *
 */
@RequestScope
@Component
@Data
public class RequestScopeModel {
	private String value;
}
