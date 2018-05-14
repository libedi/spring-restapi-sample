package com.libedi.demo.customer.service;

import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CacheKey;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

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
@CacheDefaults(cacheName = "customer")
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
	@CacheResult
	public Customer getCustomer(@CacheKey final Integer customerId) {
		return this.customerMapper.selectCustomerList(customerId).stream().findFirst().orElse(null);
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
	 * @param customerId
	 * @param customer
	 */
	@Transactional
	@CachePut
	public void updateCustomer(@CacheKey final int customerId, @CacheValue final Customer customer) {
		customer.setCustomerId(customerId);
		this.customerMapper.updateCustomer(customer);
	}

	/**
	 * delete customer
	 * @param customerId
	 */
	@Transactional
	@CacheRemove
	public void deleteCustomer(@CacheKey final int customerId) {
		this.customerMapper.deleteCustomer(customerId);
	}

}
