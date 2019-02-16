package com.ltchen.demo;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

/**
 * reference: http://zetcode.com/articles/jerseyembeddedjetty/
 */
public class App {
    public static void main( String[] args ) {
        // 新建 jetty server
        Server server = new Server(9999);
        // 设置 context path
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ctx.setContextPath("/");
        server.setHandler(ctx);
        // 将那些路径加入 servlet container
        ServletHolder holder = ctx.addServlet(ServletContainer.class, "/*");
        holder.setInitOrder(1);
        // 设置在哪里找处理类
        holder.setInitParameter("jersey.config.server.provider.packages", "com.ltchen.demo.jersey.container.jetty.servlet");

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
