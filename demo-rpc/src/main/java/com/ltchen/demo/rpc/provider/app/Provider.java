package com.ltchen.demo.rpc.provider.app;

import com.ltchen.demo.rpc.provider.skeleton.CalculateSkeleton;

import java.io.IOException;

/**
 * Created by ltchen on 2018/12/4.
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        new CalculateSkeleton().run();
    }
}
