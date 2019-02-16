package com.ltchen.demo.jersey.container.jetty.servlet;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by ltchen on 2018/12/6.
 */
@Path("message")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class Message {

    @GET
    @Path("get")
    public String getMessage() {
        return "message.";
    }
}
