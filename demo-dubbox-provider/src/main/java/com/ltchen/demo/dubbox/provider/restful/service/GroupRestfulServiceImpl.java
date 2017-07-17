package com.ltchen.demo.dubbox.provider.restful.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ltchen.demo.dubbox.api.bean.Group;
import com.ltchen.demo.dubbox.api.restful.service.GroupRestfulService;
import com.ltchen.demo.dubbox.api.service.GroupService;
import com.ltchen.demo.dubbox.provider.service.GroupServiceImpl;

@Service("groupRestfulServiceImpl")
@Path("groups")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class GroupRestfulServiceImpl implements GroupRestfulService {

	@Autowired
	private GroupService groupService = new GroupServiceImpl();
	
	@Override
	@GET
	@Path("")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public List<Group> getAll() {
		return groupService.getAll();
	}

	@Override
	@GET
	@Path("{id : \\d+}")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public Group get(@PathParam("id") String id) {
		return groupService.get(id);
	}

	@Override
	@POST
	@Path("")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public Group add(Group group) {
		return groupService.add(group);
	}

	@Override
	@PUT
	@Path("")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public Group update(Group group) {
		return groupService.update(group);
	}

	@Override
	@DELETE
	@Path("{id : \\d+}")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public void delete(@PathParam("id") String id) {
		groupService.delete(id);
	}

}
