package com.libedi.demo.user.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDefaultInfo {
	@NotEmpty
	private String username;
	@NotEmpty
	private String phoneNumber;
}
