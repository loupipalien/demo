package com.ltchen.demo.common.bean;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -920885219583451743L;
	
	//用户id -> ldap.inetOrgPerson.uid
	private int id;
	//用户别名 -> ldap.person.cn
	private String alias;	
	//用户名 -> ldap.person.sn
	private String username; 
	//用户密码 -> ldap.person.userPassword
	private String password;
	//用户电话 -> ldap.origanizationalPerson.telephoneNumber
	private String telephone;
	//用户邮箱 -> ldap.inetOrgPerson.mail
	private String email;
	//用户描述 -> ldap.person.description
	private String description;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
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
	
	@Override
	public String toString() {
		return "User [id=" + id + ", alias=" + alias + ", username=" + username + ", password=" + password
				+ ", telephone=" + telephone + ", email=" + email + ", description=" + description + "]";
	}
	
	public User(){}
	
	public User(String alias, String username, String password, String telephone, String email, String description) {
		super();
		this.alias = alias;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.description = description;
	}
	public User(int id, String alias, String username, String password, String telephone, String email,String description) {
		super();
		this.id = id;
		this.alias = alias;
		this.username = username;
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.description = description;
	}
	
}
