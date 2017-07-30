package com.ltchen.demo.hamcrest.bean;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Biscuit {
	private String name;
	
	public Biscuit(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getChocolateChipCount() {
		return 10;
	}

	public int getHazelnutCount() {
		return 3;
	}
	
	@Override
	public boolean equals(Object that) {
		return EqualsBuilder.reflectionEquals(this, that, "name");
	}
	
}
