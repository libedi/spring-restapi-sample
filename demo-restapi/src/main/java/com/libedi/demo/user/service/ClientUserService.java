package com.libedi.demo.user.service;

import org.springframework.stereotype.Service;

@Service
public class ClientUserService implements UserService {

	@Override
	public String getUser() {
		return "Client User";
	}

}
