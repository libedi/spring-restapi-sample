package com.libedi.demo.customer.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.libedi.demo.framework.model.ValidationMarker.Create;
import com.libedi.demo.framework.model.ValidationMarker.Update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer
 * @author Sangjun, Park
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
	@NotNull(groups = Create.class)
	private Integer customerId;
	@NotEmpty(groups = {Create.class, Update.class})
	private String customerName;
	@NotEmpty(groups = Update.class)
	private String company;
}
