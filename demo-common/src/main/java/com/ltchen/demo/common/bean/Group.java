package com.ltchen.demo.common.bean;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable{

	private static final long serialVersionUID = -4282869509200042886L;
	
	private int id;
	private String groupName;
	private String description;
	private List<User> users;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	@Override
	public String toString() {
		return "Group [id=" + id + ", groupName=" + groupName + ", description=" + description + ", users=" + users
				+ "]";
	}
	
	public Group(){};
	
	public Group(String groupName, String description) {
		super();
		this.groupName = groupName;
		this.description = description;
	}
	
	public Group(int id, String groupName, String description) {
		super();
		this.id = id;
		this.groupName = groupName;
		this.description = description;
	}
	
}
