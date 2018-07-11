package com.libedi.demo.user.control;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.libedi.demo.user.model.UserInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController {
	
	@GetMapping
	@ResponseBody
	public void getUser() {
		throw new IllegalArgumentException("Controller Exception!");
	}
	
	@PostMapping
	@ResponseBody
	public void testObjectValidation(@RequestBody @Valid UserInfo userInfo) {
		log.info("Test Object Validation");
	}
}
