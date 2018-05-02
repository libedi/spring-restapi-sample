package com.libedi.demo.customer.mapper;

import static org.junit.Assert.assertEquals;
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
import org.springframework.util.CollectionUtils;

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
	
	@Test
	public void test03_insertCustomer() throws Exception {
		Customer newCustomer = new Customer();
		newCustomer.setCustomerId(2);
		newCustomer.setCompany("Kakao");
		newCustomer.setCustomerName("Sangjun,Park");
		this.customerMapper.insertCustomer(newCustomer);
	}
	
	@Test
	public void test04_updateCustomer() throws Exception {
		Customer customer = new Customer(1, "Sangjun,Park", "SK C&C");
		this.customerMapper.updateCustomer(customer);
		Customer updateCustomer = this.customerMapper.selectCustomerList(1).get(0);
		assertEquals(customer.getCustomerName(), updateCustomer.getCustomerName());
		assertEquals(customer.getCompany(), updateCustomer.getCompany());
	}
	
	@Test
	public void test05_deleteCustomer() throws Exception {
		Integer customerId = 1;
		this.customerMapper.deleteCustomer(customerId);
		List<Customer> customerList = this.customerMapper.selectCustomerList(customerId);
		assertTrue(CollectionUtils.isEmpty(customerList));
	}
	
}
