package com.ltchen.demo.ldap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltchen.demo.ldap.bean.Group;
import com.ltchen.demo.ldap.bean.User;
import com.ltchen.demo.ldap.dao.GroupDao;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.service.UserService;

@Service("userServiceImpl")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
	
	@Override
	public boolean isExist(String userRdn) {
		for (User user : this.getAll()) {
			if(String.format("uid=%s,ou=people", user.getId()).equals(userRdn)){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void add(String userRdn, User user) {
		userDao.add(userRdn, user);
	}

	@Override
	public void remove(String userRdn) {
		//先删除用户组的此用户,再删除此用户
		List<Group> groups = this.getGroups(userRdn);
		this.removeFrom(userRdn, groups);
		userDao.delete(userRdn);
	}

	@Override
	public void update(String userRdn, User user) {
		userDao.update(userRdn, user);
	}

	@Override
	public User find(String userRdn) {
		User user = userDao.find(userRdn);
		user.setGroups(this.getGroups(userRdn));
		return user;
	}

	@Override
	public void rename(String oldUserRdn, String newUserRdn) {
		//重命名用户,将用户组中的此用户删除,重命名用户后再加入用户组
		List<Group> groups = this.getGroups(oldUserRdn);
		this.removeFrom(oldUserRdn, groups);
		userDao.rename(oldUserRdn, newUserRdn);
		this.addTo(newUserRdn, groups);
	}

	@Override
	public List<User> search(String userRdn, String filter) {
		return userDao.search(userRdn, filter);
	}

	/**
	 * 获取用户所在的用户组
	 * @param userRdn
	 * @return
	 */
	private List<Group> getGroups(String userRdn){
		List<Group> groups = new ArrayList<Group>();
		//获取所有用户组
		List<Group> allGroup = groupDao.getAll();
		for (Group group : allGroup) {
			//添加所属的用户组
			for (User user : group.getUsers()) {
				if(String.format("uid=%s,ou=person", user.getId()).equals(userRdn)){
					groups.add(group);
				}
			}
		}
		return groups;
	}
	
	/**
	 * 将用户从用户组中移除
	 * @param userRdn
	 * @param groups
	 */
	private void removeFrom(String userRdn, List<Group> groups){
		for (Group group : groups) {
			String groupRdn = String.format("cn=%s,ou=group",group.getGroupName());
			groupDao.removeMember(userRdn, groupRdn);
		}
	}
	
	/**
	 * 将用户添加到用户组中
	 * @param userRdn
	 * @param groups
	 */
	private void addTo(String userRdn, List<Group> groups){
		for (Group group : groups) {
			String groupRdn = String.format("cn=%s,ou=group",group.getGroupName());
			groupDao.addMember(userRdn, groupRdn);
		}
	}

}
