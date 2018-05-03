package com.libedi.demo.customer.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.libedi.demo.common.exception.ResourceConflictException;
import com.libedi.demo.customer.model.Customer;
import com.libedi.demo.customer.service.CustomerService;
import com.libedi.demo.framework.model.ValidationMarker.Create;
import com.libedi.demo.framework.model.ValidationMarker.Update;

/**
 * CustomerController for REST API
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;
	
	@Autowired
	public CustomerController(final CustomerService customerService) {
		this.customerService = customerService;
	}

	/**
	 * get Customer
	 * @param customerId
	 * @return
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Customer getCustomer(@PathVariable("id") final int customerId) {
		return this.customerService.getCustomer(customerId).orElseThrow(ResourceNotFoundException::new);
	}

	/**
	 * get Customers
	 * @return
	 */
	@GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<Customer> getCustomerList() {
		final List<Customer> customerList = this.customerService.getCustomerList();
		if(CollectionUtils.isEmpty(customerList)) {
			throw new ResourceNotFoundException();
		}
		return customerList;
	}

	/**
	 * create Customer
	 * @param customer
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public void createCustomer(@RequestBody @Validated(Create.class) final Customer customer) {
		this.customerService.getCustomer(customer.getCustomerId()).ifPresent(c -> {
			throw new ResourceConflictException();
		});
		this.customerService.createCustomer(customer);
	}

	/**
	 * update Customer
	 * @param customerId
	 * @param customer
	 * @return
	 */
	@RequestMapping(path = "/{id}", method = {RequestMethod.PUT, RequestMethod.PATCH},
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Customer updateCustomer(@PathVariable("id") final int customerId,
			@RequestBody @Validated(Update.class) final Customer customer) {
		this.customerService.getCustomer(customerId).orElseThrow(ResourceNotFoundException::new);
		customer.setCustomerId(customerId);
		this.customerService.updateCustomer(customer);
		return this.customerService.getCustomer(customerId).get();
	}

	/**
	 * delete Customer
	 * @param customerId
	 */
	@DeleteMapping(path = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void deleteCustomer(@PathVariable("id") final int customerId) {
		this.customerService.deleteCustomer(customerId);
	}
	
}
