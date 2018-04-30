package com.libedi.demo.customer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.lang.Nullable;

import com.libedi.demo.customer.model.Customer;

@Mapper
public interface CustomerMapper {

	List<Customer> selectCustomerList(@Nullable Integer customerId);


}
