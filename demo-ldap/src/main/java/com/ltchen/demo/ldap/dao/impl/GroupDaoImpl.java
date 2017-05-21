package com.ltchen.demo.ldap.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.stereotype.Component;

import com.ltchen.demo.common.bean.Group;
import com.ltchen.demo.common.util.StringUtil;
import com.ltchen.demo.ldap.dao.GroupDao;

@Component("groupDaoImpl")
public class GroupDaoImpl implements GroupDao {

	@Autowired
	private LdapTemplate ldapTemplate;
	
	public void setLdapTemplate(LdapTemplate ldapTemplate) {
	    this.ldapTemplate = ldapTemplate;
	}
	
	@Override
	public void add(String rdn, Group group) {
		Attributes attrs = new BasicAttributes();
		attrs.put("objectClass", "groupOfNames");
		attrs.put("cn", group.getGroupName());
		attrs.put("member", "");
		attrs.put("description", group.getDescription());
		this.ldapTemplate.bind(rdn, null, attrs);
	}

	@Override
	public void delete(String rdn) {
		this.ldapTemplate.unbind(rdn);
	}

	@Override
	public void update(String rdn, Group group) {
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		if(StringUtil.isNotBlank(group.getDescription())){
			Attribute descriptionAttr = new BasicAttribute("description", group.getDescription());
			ModificationItem modificationItem = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, descriptionAttr);
			modificationItems.add(modificationItem);
		}
		this.ldapTemplate.modifyAttributes(rdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));

	}

	@Override
	public Group find(String rdn) {
		return this.ldapTemplate.lookup(rdn, new AttributesMapper<Group>(){
        	@Override
        	public Group mapFromAttributes(Attributes attributes)throws NamingException {
            	return convert(attributes);
            }
        });
	}

	@Override
	public void rename(String oldRdn, String newRdn) {
		this.ldapTemplate.rename(oldRdn, newRdn);
	}

	@Override
	public List<Group> search(String rdn, String filter) {
		return this.ldapTemplate.search(rdn, filter, new AttributesMapper<Group>(){
			@Override
			 public Group mapFromAttributes(Attributes attributes)throws NamingException {
				return convert(attributes);
             }
		});
	}

	@Override
	public void addMember(String userDn, String groupRdn) {
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		Attribute memberAttr = new BasicAttribute("member", userDn);
		ModificationItem modificationItem = new ModificationItem(DirContext.ADD_ATTRIBUTE, memberAttr);
		modificationItems.add(modificationItem);
		this.ldapTemplate.modifyAttributes(groupRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
	}

	@Override
	public void deleteMember(String userDn, String groupRdn) {
		List<ModificationItem> modificationItems = new ArrayList<ModificationItem>();
		Attribute memberAttr = new BasicAttribute("member", userDn);
		ModificationItem modificationItem = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, memberAttr);
		modificationItems.add(modificationItem);
		this.ldapTemplate.modifyAttributes(groupRdn, modificationItems.toArray(new ModificationItem[modificationItems.size()]));
		
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
        }
        return group;
	}

}
