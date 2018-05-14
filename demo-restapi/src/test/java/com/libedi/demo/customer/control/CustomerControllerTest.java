package com.libedi.demo.customer.control;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.libedi.demo.customer.model.Customer;
import com.libedi.demo.customer.service.CustomerService;

/**
 * CustomerControllerTest
 * @author Sangjun, Park
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerTest {
	
	private CustomerController customerController;
	
	private CustomerService customerService;
	
	@Before
	public void init() throws Exception {
		this.customerService = mock(CustomerService.class);
		this.customerController = new CustomerController(customerService);
	}
	
	@Test
	public void test01_getCustomer() throws Exception {
		Integer customerId = 1;
		Customer expected = new Customer(customerId, "libedi", "Freelancer");
		when(this.customerService.getCustomer(customerId)).thenReturn(expected);
		Customer actual = this.customerController.getCustomer(customerId);
		assertThat(actual).isNotNull().isEqualTo(expected);
	}
	
	@Test
	public void test02_getCustomerList() throws Exception {
		List<Customer> expected = Arrays.asList(new Customer(1, "libedi", "Freelancer"));
		when(this.customerService.getCustomerList()).thenReturn(expected);
		List<Customer> actual = this.customerController.getCustomerList();
		assertThat(actual).isNotEmpty().isEqualTo(expected);
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
		Customer expected = new Customer(customerId, "Sangjun,Park", "Kakao");
		when(this.customerService.getCustomer(customerId)).thenReturn(expected);
		Customer actual = this.customerController.updateCustomer(customerId, expected);
		assertThat(actual).isNotNull().isEqualTo(expected);
	}
	
	@Test
	public void test05_deleteCustomer() throws Exception {
		Integer customerId = 1;
		this.customerController.deleteCustomer(customerId);
	}
	
}
