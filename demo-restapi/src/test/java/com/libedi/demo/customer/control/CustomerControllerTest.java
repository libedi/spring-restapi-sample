package com.libedi.demo.customer.control;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.libedi.demo.customer.model.Customer;
import com.libedi.demo.customer.service.CustomerService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
	
	private CustomerController customerController;
	
	@Before
	public void init() throws Exception {
		this.customerController = new CustomerController(new CustomerService(null));
	}
	
	@Test
	public void test01_getCustomer() throws Exception {
		Integer customerId = 1;
		Customer customer = this.customerController.getCustomer(customerId);
		assertNotNull(customer);
		assertEquals(customer.getCustomerId(), customerId);
	}
	
	@Test
	public void test02_getCustomerList() throws Exception {
		List<Customer> customerList = this.customerController.getCustomerList();
		assertNotNull(customerList);
		assertFalse(customerList.isEmpty());
	}

	@Test
	public void test03_createCustomer() throws Exception {
		Customer customer = new Customer();
		customer.setCustomerName("libedi");
		customer.setCompany("free");
		this.customerController.createCustomer(customer);
	}
	
	@Test
	public void test04_updateCustomer() throws Exception {
		Integer customerId = 1;
		Customer customer = new Customer();
		customer.setCustomerName("libedi01");
		Customer updateCustomer = this.customerController.updateCustomer(customerId, customer);
		assertEquals(customer.getCustomerName(), updateCustomer.getCustomerName());
	}
	
	@Test
	public void test05_deleteCustomer() throws Exception {
		Integer customerId = 1;
		this.customerController.deleteCustomer(customerId);
	}
	
}
