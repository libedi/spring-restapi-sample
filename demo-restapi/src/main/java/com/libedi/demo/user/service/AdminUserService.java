package com.libedi.demo.user.service;

import org.springframework.stereotype.Service;

@Service
public class AdminUserService implements UserService {

	@Override
	public String getUser() {
		return "Admin User";
	}

}
