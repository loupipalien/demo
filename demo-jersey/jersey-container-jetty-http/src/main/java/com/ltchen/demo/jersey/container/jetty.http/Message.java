package com.ltchen.demo.jersey.container.jetty.http;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.jetty.server.Request;

/**
 * Created by ltchen on 2018/12/6.
 */
@Path("message")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class Message {

    /**
     * 支持注入 org.eclipse.jetty.server.Request
     * 注意点: 在类上注解 @Singleton, 会使得所有请求都使用同一个 Request, 第一个请求的 Request 可以正确获取, 后续的请求的 Request 字段都会为 null
     * reference: https://stackoverflow.com/questions/29434312/is-it-possible-in-jersey-to-have-access-to-an-injected-httpservletrequest-inste
     */
    @Context
    private Request request;

    @GET
    @Path("get")
    public String getMessage() {
        return request.getRemoteAddr();
    }
}
