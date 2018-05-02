package com.libedi.demo.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.libedi.demo.customer.mapper.CustomerMapper;
import com.libedi.demo.customer.model.Customer;

/**
 * CustomerService
 * @author Sangjun, Park
 *
 */
@Service
public class CustomerService {
	
	private final CustomerMapper customerMapper;
	
	@Autowired
	public CustomerService(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public Optional<Customer> getCustomer(final Integer customerId) {
		return this.customerMapper.selectCustomerList(customerId).stream().findFirst();
	}

	/**
	 * 
	 * @return
	 */
	public List<Customer> getCustomerList() {
		return this.customerMapper.selectCustomerList(null);
	}

	/**
	 * 
	 * @param customer
	 */
	public void createCustomer(final Customer customer) {
		this.customerMapper.insertCustomer(customer);
	}

	/**
	 * 
	 * @param customer
	 */
	public void updateCustomer(final Customer customer) {
		this.customerMapper.updateCustomer(customer);
	}

	/**
	 * 
	 * @param customerId
	 */
	public void deleteCustomer(final Integer customerId) {
		this.customerMapper.deleteCustomer(customerId);
	}

}
