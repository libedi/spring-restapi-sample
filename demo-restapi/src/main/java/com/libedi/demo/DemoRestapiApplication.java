package com.libedi.demo;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libedi.demo.common.model.ErrorResponse;

@SpringBootApplication
public class DemoRestapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoRestapiApplication.class, args);
	}
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Configuration to errorAttributes
	 * @return
	 */
	@Bean
	public ErrorAttributes errorAttributes() {
		return new DefaultErrorAttributes() {
			@SuppressWarnings("unchecked")
			@Override
			public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
				Integer status = (Integer) webRequest.getAttribute("javax.servlet.error.status_code",
						RequestAttributes.SCOPE_REQUEST);
				return objectMapper.convertValue(
						new ErrorResponse(
								status == null ? "None" : HttpStatus.valueOf(status).getReasonPhrase(), ""),
						Map.class);
			}
		};
	}
}
