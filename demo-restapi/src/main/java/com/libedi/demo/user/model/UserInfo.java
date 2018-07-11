package com.libedi.demo.user.model;

import com.libedi.demo.framework.model.ObjectValid;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserInfo {
	@ObjectValid
	private UserDefaultInfo userDefaultInfo;
	
	@ObjectValid
	private UserDetailInfo userDetailInfo;
}
