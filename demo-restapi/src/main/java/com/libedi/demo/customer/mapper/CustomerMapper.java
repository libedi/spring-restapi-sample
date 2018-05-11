package com.libedi.demo.customer.mapper;

import java.util.List;

import javax.cache.annotation.CacheDefaults;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.cache.annotation.CacheResult;
import javax.cache.annotation.CacheValue;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import com.libedi.demo.customer.model.Customer;

/**
 * CustomerMapper
 * @author Sangjun, Park
 *
 */
@Mapper
@CacheDefaults(cacheName = "customer")
public interface CustomerMapper {

	@CacheResult
	List<Customer> selectCustomerList(@Nullable Integer customerId);

	void insertCustomer(Customer customer);

	@CachePut
	void updateCustomer(@CacheValue Customer customer);

	@CacheRemove
	void deleteCustomer(Integer customerId);
	
}
