package com.libedi.demo.customer.control;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libedi.demo.customer.model.Customer;
import com.libedi.demo.customer.service.CustomerService;

/**
 * CustomerController Spring MVC Test
 * @author Sangjun, Park
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerControllerMvcTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private CustomerService customerService;
	
	@Test
	public void test01_getCustomer() throws Exception {
		int customerId = 1;
		Customer expected = new Customer(customerId, "libedi", "Freelancer");
		given(this.customerService.getCustomer(customerId)).willReturn(expected);
		this.mockMvc.perform(get("/customers/" + customerId).accept(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.customerName").value("libedi"))
		;
	}
	
	@Test
	public void test02_getCustomerList() throws Exception {
		given(this.customerService.getCustomerList()).willReturn(Arrays.asList(new Customer(1, "libedi", "Freelancer")));
		this.mockMvc.perform(get("/customers").accept(MediaType.APPLICATION_JSON_UTF8))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$").isArray())
		;
	}
	
	@Test
	public void test03_createCustomer() throws Exception {
		int customerId = 2;
		this.mockMvc.perform(post("/customers")
				.characterEncoding(StandardCharsets.UTF_8.toString())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(new Customer(customerId, "Sangjun,Park", "Kakao"))))
			.andDo(print())
			.andExpect(status().isCreated())
		;
	}
	
	@Test
	public void test04_updateCustomer() throws Exception {
		int customerId = 1;
		Customer expected = new Customer(customerId, "Sangjun,Park", "Kakao");
		given(this.customerService.getCustomer(customerId)).willReturn(expected);
		this.mockMvc.perform(put("/customers/" + customerId)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.characterEncoding(StandardCharsets.UTF_8.toString())
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(this.objectMapper.writeValueAsString(expected)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$").hasJsonPath())
		;
	}
	
	@Test
	public void test05_deleteCustomer() throws Exception {
		int customerId = 1;
		this.mockMvc.perform(delete("/customers/" + customerId))
			.andDo(print())
			.andExpect(status().isOk())
		;
	}
}
