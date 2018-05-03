package com.libedi.demo.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import com.libedi.demo.customer.model.Customer;

/**
 * CustomerMapper
 * @author Sangjun, Park
 *
 */
@Mapper
public interface CustomerMapper {

	List<Customer> selectCustomerList(@Nullable Integer customerId);

	void insertCustomer(Customer customer);

	void updateCustomer(Customer customer);

	void deleteCustomer(Integer customerId);
	
}
