package me.supercube.security.enums;

public enum UserStatusEnum {
	ACTIVE("活动"), INACTIVE("禁用");

	private String description;

	UserStatusEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
