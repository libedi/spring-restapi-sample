package com.libedi.demo.customer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	public CustomerService(final CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	/**
	 * get customer
	 * @param customerId
	 * @return
	 */
	public Optional<Customer> getCustomer(final Integer customerId) {
		return this.customerMapper.selectCustomerList(customerId).stream().findFirst();
	}

	/**
	 * get customer list
	 * @return
	 */
	public List<Customer> getCustomerList() {
		return this.customerMapper.selectCustomerList(null);
	}

	/**
	 * create customer
	 * @param customer
	 */
	@Transactional
	public void createCustomer(final Customer customer) {
		this.customerMapper.insertCustomer(customer);
	}

	/**
	 * update customer
	 * @param customer
	 */
	@Transactional
	public void updateCustomer(final Customer customer) {
		this.customerMapper.updateCustomer(customer);
	}

	/**
	 * delete customer
	 * @param customerId
	 */
	@Transactional
	public void deleteCustomer(final int customerId) {
		this.customerMapper.deleteCustomer(customerId);
	}

}
