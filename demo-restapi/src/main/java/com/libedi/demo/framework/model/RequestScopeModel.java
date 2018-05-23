package com.libedi.demo.framework.model;

import org.springframework.web.context.annotation.RequestScope;

import lombok.Data;

@RequestScope
@Data
public class RequestScopeModel {
	private String value;
}
