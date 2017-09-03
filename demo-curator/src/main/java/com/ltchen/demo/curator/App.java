package com.ltchen.demo.curator;

public class App {

    private static String zkConnStr = "192.168.0.126:2181";

    public static void main(String[] args) throws Exception {
       ZKCliHelper zkCliHelper = new ZKCliHelper();
       zkCliHelper.setZkConnStr(zkConnStr);
       zkCliHelper.init();
       zkCliHelper.createPath("/ltchen", "wawo");
//       System.out.println(zkCliHelper.getData("/ltchen"));
       zkCliHelper.destory();
    }

  
}
