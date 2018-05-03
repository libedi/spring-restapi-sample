package com.libedi.demo.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ErrorResponse
 * @author Sangjun, Park
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_NULL)
public class ErrorResponse {
	
	private String errorField;
	private String errorMessage;
	
	public ErrorResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
