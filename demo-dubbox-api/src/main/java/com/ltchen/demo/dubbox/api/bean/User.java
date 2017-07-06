package com.ltchen.demo.dubbox.api.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -8020513365572409982L;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User() {}
	
	public User(String name){
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "User [name=" + name + "]";
	}
	
	
}
