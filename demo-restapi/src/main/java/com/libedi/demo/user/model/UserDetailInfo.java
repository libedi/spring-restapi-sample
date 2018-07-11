package com.libedi.demo.user.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDetailInfo {
	@NotEmpty
	private String address;
	@NotEmpty
	private String zipCode;
}
