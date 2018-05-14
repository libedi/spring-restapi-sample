package com.libedi.demo.customer.control;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.libedi.demo.customer.model.Customer;
import com.libedi.demo.customer.service.CustomerService;

/**
 * CustomerController Spring MVC Test
 * @author Sangjun, Park
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class CustomerControllerMvcTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void testGetCustomer() throws Exception {
		int customerId = 1;
		Customer expected = new Customer(customerId, "libedi", "Freelancer");
		given(this.customerService.getCustomer(customerId)).willReturn(expected);
		mockMvc.perform(get("/customers/" + customerId).accept(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.customerName").value("libedi"))
		;
	}
}
