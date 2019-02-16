package com.ltchen.demo;

import com.ltchen.demo.jersey.container.jetty.http.Message;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

/**
 * reference: https://jersey.github.io/documentation/latest/deployment.html#deployment.http
 */
public class App {
    public static void main( String[] args ) {
        // 基础路径
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();
        // 配置提供 rest 接口的类
        ResourceConfig config = new ResourceConfig(Message.class);
        // 创建 Jetty Http Server
        Server server = JettyHttpContainerFactory.createServer(baseUri, config);
        try {
            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.destroy();
        }
    }
}
