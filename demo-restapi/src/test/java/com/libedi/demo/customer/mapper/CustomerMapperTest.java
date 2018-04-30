package com.libedi.demo.customer.mapper;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import com.libedi.demo.customer.model.Customer;

@RunWith(SpringRunner.class)
@MybatisTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerMapperTest {
	
	@Autowired
	private CustomerMapper customerMapper;
	
	@Before
	public void init() throws Exception {
		assertNotNull(this.customerMapper);
	}
	
	@Test
	public void test01_selectCustomer() throws Exception {
		int customerId = 1;
		List<Customer> customerList = this.customerMapper.selectCustomerList(customerId);
		assertNotNull(customerList);
		assertFalse(customerList.isEmpty());
		Customer customer = customerList.get(0);
		assertNotNull(customer);
		assertTrue(customerId == customer.getCustomerId());
	}
	
	@Test
	public void test02_selectCustomerList() throws Exception {
		List<Customer> customerList = this.customerMapper.selectCustomerList(null);
		assertNotNull(customerList);
		assertFalse(customerList.isEmpty());
	}
	
}
