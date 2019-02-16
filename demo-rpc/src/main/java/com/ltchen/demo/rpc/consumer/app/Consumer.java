package com.ltchen.demo.rpc.consumer.app;

import com.ltchen.demo.rpc.consumer.stub.CalculateStub;
import com.ltchen.demo.rpc.provider.app.Calculate;

/**
 * Created by ltchen on 2018/12/4.
 */
public class Consumer {

    public static void main(String[] args) {
        Calculate stub = new CalculateStub();
        int result = stub.add(1, 1);
        System.out.println("result is: " + result);
    }
}
