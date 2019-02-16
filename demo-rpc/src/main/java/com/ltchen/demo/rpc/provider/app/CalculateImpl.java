package com.ltchen.demo.rpc.provider.app;

/**
 * Created by ltchen on 2018/12/4.
 */
public class CalculateImpl implements Calculate {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}
