package com.libedi.demo.customer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.libedi.demo.framework.model.ValidMarker.Create;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Sangjun, Park
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@NotNull(groups = Create.class)
	private Integer customerId;
	@NotEmpty(groups = Create.class)
	private String customerName;
	private String company;
}
