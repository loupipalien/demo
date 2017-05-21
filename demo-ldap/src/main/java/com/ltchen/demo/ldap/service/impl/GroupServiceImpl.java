package com.ltchen.demo.ldap.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ltchen.demo.common.bean.Group;
import com.ltchen.demo.ldap.dao.GroupDao;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.ldap.service.GroupService;

@Service("groupServiceImpl")
public class GroupServiceImpl implements GroupService {

	@Autowired
	private GroupDao groupDao;
	@Autowired
	private UserDao userDao;
	
	@Override
	public void add(String rdn, Group group) {
		groupDao.add(rdn, group);
	}

	@Override
	public void delete(String rdn) {
		groupDao.delete(rdn);
	}

	@Override
	public void update(String rdn, Group group) {
		groupDao.update(rdn, group);
	}

	@Override
	public Group find(String rdn) {
		return groupDao.find(rdn);
	}

	@Override
	public void rename(String oldRdn, String newRdn) {
		groupDao.rename(oldRdn, newRdn);
	}

	@Override
	public List<Group> search(String rdn, String filter) {
		return groupDao.search(rdn, filter);
	}

	@Override
	public void addUserToGroup(String userRdn, String groupRdn) {
		String userDn = String.format("%s,%s", userRdn, userDao.getBaseDn(userRdn));
		groupDao.addMember(userDn, groupRdn);
		
	}

	@Override
	public void removeUserFromGroup(String userRdn, String groupRdn) {
		String userDn = String.format("%s,%s", userRdn, userDao.getBaseDn(userRdn));
		groupDao.deleteMember(userDn, groupRdn);
	}

}
