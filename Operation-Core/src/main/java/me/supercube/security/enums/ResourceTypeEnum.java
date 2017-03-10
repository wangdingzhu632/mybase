package me.supercube.security.enums;

public enum ResourceTypeEnum {
	MENU("菜单"), SIGOPTION("签名");

	private String description;

	ResourceTypeEnum(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

}
