package com.ltchen.demo.ldap.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.stereotype.Component;

import com.ltchen.demo.ldap.bean.Group;
import com.ltchen.demo.ldap.bean.User;
import com.ltchen.demo.ldap.dao.GroupDao;
import com.ltchen.demo.ldap.dao.UserDao;
import com.ltchen.demo.util.common.StringUtil;

@Component("groupDaoImpl")
public class GroupDaoImpl implements GroupDao {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LdapTemplate ldapTemplate;
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
	    this.ldapTemplate = ldapTemplate;
	}
	

	@Override
	public List<Group> getAll() {
		return this.search("ou=group", "(objectClass=groupOfNames)");
	}
	
	@Override
	public void add(String groupRdn, Group group) {
		Attributes attrs = new BasicAttributes();
		attrs.put("objectClass", "groupOfNames");
		attrs.put("cn", group.getGroupName());
		attrs.put("member", "");
		attrs.put("description", group.getDescription());
		this.ldapTemplate.bind(groupRdn, null, attrs);
	}

	@Override
	public void delete(String groupRdn) {
		this.ldapTemplate.unbind(groupRdn);
	}

	@Override
	public void update(String groupRdn, Group group) {
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		if(StringUtil.isNotBlank(group.getDescription())){
			Attribute descriptionAttr = new BasicAttribute("description", group.getDescription());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, descriptionAttr);
			modificationItems.add(modificationItem);
		}
		this.ldapTemplate.modifyAttributes(groupRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));

	}

	@Override
	public Group find(String groupRdn) {
		return this.ldapTemplate.lookup(groupRdn, new AttributesMapper<Group>(){
        	@Override
        	public Group mapFromAttributes(Attributes attributes)throws NamingException {
            	return convert(attributes);
            }
        });
	}

	@Override
	public void rename(String oldGroupRdn, String newGroupRdn) {
		this.ldapTemplate.rename(oldGroupRdn, newGroupRdn);
	}

	@Override
	public List<Group> search(String groupRdn, String filter) {
		return this.ldapTemplate.search(groupRdn, filter, new AttributesMapper<Group>(){
			@Override
			 public Group mapFromAttributes(Attributes attributes)throws NamingException {
				return convert(attributes);
             }
		});
	}

	@Override
	public void addMember(String userRdn, String groupRdn) {
		String userDn = String.format("%s,%s", userRdn, this.getBaseDn());
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		Attribute memberAttr = new BasicAttribute("member", userDn);
		ModificationItem modificationItem = new ModificationItem(DirContext.ADD_ATTRIBUTE, memberAttr);
		modificationItems.add(modificationItem);
		this.ldapTemplate.modifyAttributes(groupRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
	}

	@Override
	public void removeMember(String userRdn, String groupRdn) {
		String userDn = String.format("%s,%s", userRdn, this.getBaseDn());
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		Attribute memberAttr = new BasicAttribute("member", userDn);
		ModificationItem modificationItem = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, memberAttr);
		modificationItems.add(modificationItem);
		this.ldapTemplate.modifyAttributes(groupRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
		
	}
	 
	@Override
	public String getBaseDn() {
		LdapContextSource lcs= (LdapContextSource)ldapTemplate.getContextSource();
		return lcs.getBaseLdapPathAsString();
	}
	
	/**
	 * 将Attributes对象转换为Group对象
	 * @param attributes Attributes对象
	 * @return 
	 * @throws NamingException
	 */
	private Group convert(Attributes attributes)throws NamingException{
		Group group = new Group();
        if(attributes != null){
            Attribute groupNameAttr = attributes.get("cn");
            if(groupNameAttr != null){
            	group.setGroupName((String)groupNameAttr.get());
            }
            Attribute descriptionAttr = attributes.get("description");
            if(descriptionAttr!=null){
            	group.setDescription((String)descriptionAttr.get());
            }
            Attribute memberAttr = attributes.get("member");
            if(memberAttr != null){
            	NamingEnumeration<String> members = (NamingEnumeration<String>) memberAttr.getAll();
            	if(members != null){
            		group.setUsers(this.convertMembers(members));
            	}
            }
        }
        return group;
	}

	/**
	 * 将member属性转换为List<User>
	 * @param members
	 * @return
	 */
	private List<User> convertMembers(NamingEnumeration<String> members){
		List<User> users = new ArrayList<User>();
		String baseDn = this.getBaseDn();
		try {
			while(members.hasMore()){
				String userDn = members.next();
				//过滤空member
				if(StringUtil.isBlank(userDn)){
					continue;
				}
				String userRdn = userDn.substring(0, userDn.length()-baseDn.length()-1);
				User user = userDao.find(userRdn);
				//过滤已删除member
				if(user.getId() > 0){
					users.add(user);
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return users;
	}

}
