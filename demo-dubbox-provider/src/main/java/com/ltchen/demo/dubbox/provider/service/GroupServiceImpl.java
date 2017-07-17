package com.ltchen.demo.dubbox.provider.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ltchen.demo.dubbox.api.bean.Group;
import com.ltchen.demo.dubbox.api.bean.User;
import com.ltchen.demo.dubbox.api.service.GroupService;

@Service("groupServiceImpl")
public class GroupServiceImpl implements GroupService {

	@Override
	public List<Group> getAll() {
		List<User> users = new ArrayList<User>();
		users.add(new User("1","ltchen"));
		users.add(new User("2","loupipalien"));
		List<Group> groups = new ArrayList<Group>();
		groups.add(new Group("1", "groupA", users));
		groups.add(new Group("2", "groupB", users));
		System.out.println("get all groups.");
		return groups;
	}

	@Override
	public Group get(String id) {
		List<User> users = new ArrayList<User>();
		users.add(new User("1","ltchen"));
		users.add(new User("2","loupipalien"));
		System.out.println("get group that id is " + id);
		return new Group(id, "group", users);
	}

	@Override
	public Group add(Group group) {
		System.out.println("add group that is " + group);
		return group;
	}

	@Override
	public Group update(Group group) {
		System.out.println("update group that is " + group);
		return group;
	}

	@Override
	public void delete(String id) {
		System.out.println("delete group that id is " + id);
	}

}
