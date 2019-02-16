package com.ltchen.demo;

import com.ltchen.demo.jdk.spi.SpiService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * SPI DEMO
 */
public class App {
    public static void main( String[] args ) throws SQLException {
        /**
         * String url = "jdbc:xxxx://xxxx:xxxx/xxxx";
         * Connection conn = DriverManager.getConnection(url,"", "");
         */
        ServiceLoader<SpiService> serviceLoader = ServiceLoader.load(SpiService.class);
        Iterator<SpiService> it = serviceLoader.iterator();
        while (it.hasNext()) {
            SpiService spiService = it.next();
            System.out.println(spiService.getClass().getName() + " : " + spiService.sayHi("World"));
        }
    }
}
