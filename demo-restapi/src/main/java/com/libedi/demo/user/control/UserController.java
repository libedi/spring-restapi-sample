package com.libedi.demo.user.control;

import java.beans.PropertyEditorSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.libedi.demo.user.model.UserId;
import com.libedi.demo.user.model.UserInfo;
import com.libedi.demo.user.service.UserServiceFactoryBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserServiceFactoryBean factoryBean;
	
	@GetMapping("/")
	@ResponseBody
	public void getUser() {
		throw new IllegalArgumentException("Controller Exception!");
	}
	
	@PostMapping
	@ResponseBody
	public void testObjectValidation(@RequestBody @Valid UserInfo userInfo) {
		log.info("Test Object Validation");
	}
	
	@GetMapping("/name/{id}")
	@ResponseBody
	public String getUsername(@PathVariable("id") UserId userId) {
		return this.factoryBean.getUserService(userId).getUser();
	}
	
	@InitBinder
	public void initBinder(final DataBinder dataBinder) {
		dataBinder.registerCustomEditor(UserId.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(final String text) throws IllegalArgumentException {
				this.setValue(UserId.fromValue(text));
			}
		});
	}
	
}
