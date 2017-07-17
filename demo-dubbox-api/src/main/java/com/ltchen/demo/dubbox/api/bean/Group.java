package com.ltchen.demo.dubbox.api.bean;

import java.io.Serializable;
import java.util.List;

public class Group implements Serializable {

	private static final long serialVersionUID = -3644938716696862238L;

	private String id;
	private String name;
	private List<User> users;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Group() {
	}

	public Group(String id, String name, List<User> users) {
		this.id = id;
		this.name = name;
		this.users = users;
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + ", users=" + users + "]";
	}

}
