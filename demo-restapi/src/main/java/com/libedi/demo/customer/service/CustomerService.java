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
	public Optional<Customer> getCustomer(Integer customerId) {
		return Optional.ofNullable(customerMapper.selectCustomerList(customerId).get(0));
	}

	public List<Customer> getCustomerList() {
		return null;
	}

	public void createCustomer(Customer customer) {
		
	}

	public void updateCustomer(Customer customer) {
		
	}

	public void deleteCustomer(Integer customerId) {
		
	}

}
