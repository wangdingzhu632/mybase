package me.supercube.common.model;

import java.io.Serializable;

public class KeyValue implements Serializable{

	private static final long serialVersionUID = -8429625424720409645L;

	private String key;
	private String value;

	public KeyValue(){
		super();
	}

	public KeyValue(String key,String value){
		super();
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
