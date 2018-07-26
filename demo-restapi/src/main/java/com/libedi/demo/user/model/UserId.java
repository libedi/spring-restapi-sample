package com.libedi.demo.user.model;

import java.util.Arrays;

public enum UserId {
	CLIENT("client")
	, ADMIN("admin")
	;
	
	private String value;
	private UserId(final String value) {
		this.value = value;
	}
	
	public static UserId fromValue(final String value) {
		return Arrays.stream(UserId.values())
				.filter(userId -> userId.getValue().equals(value))
				.findFirst()
				.get();
	}

	public String getValue() {
		return value;
	}
}
