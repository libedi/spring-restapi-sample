package com.libedi.demo.customer.control;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.target.ThreadLocalTargetSource;
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
import com.libedi.demo.framework.model.NotEmpty;
import com.libedi.demo.framework.model.NotNull;
import com.libedi.demo.framework.model.Pattern;
import com.libedi.demo.framework.model.RequestScopeModel;
import com.libedi.demo.framework.model.ThreadScopeModel;
import com.libedi.demo.framework.model.ValidationMarker.Create;
import com.libedi.demo.framework.model.ValidationMarker.Update;

import lombok.RequiredArgsConstructor;

/**
 * CustomerController for REST API
 * @author Sangjun, Park
 *
 */
@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
	
	private final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	private final CustomerService customerService;
	
	@Autowired
	private ThreadLocalTargetSource threadLocalTargetSource;
	
	@Autowired
	private RequestScopeModel requestScopeModel;
	
//	@Autowired
//	public CustomerController(final CustomerService customerService) {
//		this.customerService = customerService;
//	}

	/**
	 * get Customer
	 * @param customerId
	 * @return
	 */
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public Customer getCustomer(@PathVariable("id") final int customerId) {
		// get thread-scope bean using ThreadLocalTargetSource
		ThreadScopeModel model = (ThreadScopeModel) threadLocalTargetSource.getTarget();
		// access thread-scope bean using ThreadLocalTargetSource
		logger.info("ThreadScope value : {}", ((ThreadScopeModel) threadLocalTargetSource.getTarget()).getValue());
		model.setValue("test" + customerId);
		
		// access request-scope bean
		logger.info("RequestScope value : {}", requestScopeModel.getValue());
		// get request-scope bean
		requestScopeModel.setValue("test" + customerId);
		return Optional.ofNullable(this.customerService.getCustomer(customerId)).orElseThrow(ResourceNotFoundException::new);
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
		Optional.ofNullable(this.customerService.getCustomer(customer.getCustomerId())).ifPresent(c -> {
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
		Optional.ofNullable(this.customerService.getCustomer(customerId)).orElseThrow(ResourceNotFoundException::new);
		this.customerService.updateCustomer(customerId, customer);
		return this.customerService.getCustomer(customerId);
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
	
	/**
	 * custom Map validate annotation
	 * @param map
	 */
	@PostMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	@NotNull({"test1", "test2", "test3"})
	@NotEmpty("test2")
	@Pattern(value = {"test3"}, regExp = "^[0-9]*$")
	public void test(@RequestBody final Map<String, Object> map) {
		map.keySet().stream().forEach(logger::info);
	}
	
}
