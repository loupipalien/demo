package com.ltchen.demo.common.bean;

import java.io.Serializable;
import java.util.List;

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
	private List<Group> groups;
	
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
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", alias=" + alias + ", username=" + username + ", password=" + password
				+ ", telephone=" + telephone + ", email=" + email + ", description=" + description + ", groups="
				+ groups + "]";
	}
	
	public User(){}
	
	public User(int id, String password, String telephone, String email, String description) {
		super();
		this.id = id;
		this.alias = String.valueOf(id);
		this.username = String.valueOf(id);
		this.password = password;
		this.telephone = telephone;
		this.email = email;
		this.description = description;
	}

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
	
	 // 重写equals(Object obj)方法
    @Override
    public boolean equals(Object obj) {
          if (this == obj) {
                return true;
          }
          if (obj == null) {
                return false;
          }
          if (!(obj instanceof User)) {
                return false;
          }
          final User user = (User) obj;
          if (this.getId() != user.getId()) {
                return false;
          }
          if (!this.getUsername().equals(user.getUsername())) {
              return false;
        }
          return true;
    }
    //重写hashCode()方法
    @Override
    public int hashCode() {
          return this.username.hashCode() + id;
    }

}
