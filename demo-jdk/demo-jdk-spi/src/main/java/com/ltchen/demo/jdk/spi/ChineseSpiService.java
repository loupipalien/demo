package com.ltchen.demo.jdk.spi;

/**
 * Created by ltchen on 2018/11/26.
 */
public class ChineseSpiService implements SpiService {
    @Override
    public String sayHi(String msg) {
        return "你好, " + msg;
    }
}
