package com.ltchen.demo.dubbox.provider.service;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.ltchen.demo.dubbox.api.bean.User;
import com.ltchen.demo.dubbox.api.service.UserService;

@Service("userServiceImpl")
@Path("users")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class UserServiceImpl implements UserService {

	@Override
	@GET
	@Path("sayHello")
	@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
	public String sayHello() {
		return "Hello";
	}

}
