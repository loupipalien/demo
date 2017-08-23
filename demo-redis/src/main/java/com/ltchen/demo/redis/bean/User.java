package com.ltchen.demo.redis.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 3383622940693856352L;
	
	private String id;
	private String username;
	private String password;
	private String telephone;
	private String email;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public User(String id, String username, String password, String telephone, String email, String description) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", telephone=" + telephone
				+ ", email=" + email + ", description=" + description + "]";
	}
	
}
