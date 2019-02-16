package com.ltchen.demo;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.*;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, ClassNotFoundException {
        System.out.println( "Hello World!" );
//        Socket socket = new Socket();
//        SocketAddress addr = new InetSocketAddress("10.203.13.75", 9999);
//        System.out.println("1:" + socket.getLocalAddress().getHostAddress());
//        socket.connect(addr, 1000);
//        System.out.println("2:" + socket.getLocalAddress().getHostAddress());
        System.out.println(Class.class.getClassLoader());
        System.out.println(App.class.getClassLoader());
        System.out.println(Thread.currentThread().getContextClassLoader());
//        System.out.println(App.class.getClassLoader());
        for (URL url: ((URLClassLoader)App.class.getClassLoader()).getURLs()) {
            System.out.println(url.getPath());
        }
    }

    public static String appendReplacement() {
        Pattern p = Pattern.compile("cat");
        Matcher m = p.matcher("one cat two cats in the yard");
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "dog");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static int getAvailablePort() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket();
            ss.bind(null);
            return ss.getLocalPort();
        } catch (IOException e) {
            return -1;
        } finally {
            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
    }
}


class A {
    public void method() {
        System.out.println("A");
    }
}

class B extends A {
    public void method() {
        super.method();
    }
}