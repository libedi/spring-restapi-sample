package com.libedi.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.libedi.demo.user.model.UserId;

@Component
public class UserServiceFactoryBean {
	@Autowired
	private ApplicationContext context;

	public UserService getUserService(UserId userId) {
		switch(userId) {
		case ADMIN:
			return this.context.getBean("adminUserService", AdminUserService.class);
		case CLIENT:
			return this.context.getBean("clientUserService", ClientUserService.class);
		default:
			return null;
		}
	}
}
