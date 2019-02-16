package com.ltchen.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ltchen on 2019/1/9.
 * 加权轮询调度算法
 */
public class WeightRoundRobin {
    public static class Server {
        private String address;
        private int weight;

        public Server(String address, int weight) {
            this.address = address;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Server{address='" + address + ", weight=" + weight + '}';
        }
    }

    private List<Server> servers;
    private int currentIndex = -1;
    private int currentWeight = 0;
    private int maximumWeight;
    private int greatestCommonDivisor;
    private int serverCount;

    public WeightRoundRobin(List<Server> servers) {
        this.servers = servers;
        List<Integer> list = servers.stream().map(server -> server.weight).collect(Collectors.toList());
        maximumWeight = computeMaximum(list);
        greatestCommonDivisor = computeGreatestCommonDivisor(list);
        serverCount = list.size();
    }

    private int computeMaximum(List<Integer> list) {
        if (list == null && list.isEmpty()) {
            throw new IllegalArgumentException("List must not null or empty.");
        }

        int max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            max = Math.max(max, list.get(i));
        }
        return max;
    }

    private int computeGreatestCommonDivisor(int a, int b) {
        return a % b == 0 ? b : computeGreatestCommonDivisor(b, a % b);
    }

    private int computeGreatestCommonDivisor(List<Integer> list) {
        if (list == null && list.isEmpty()) {
            throw new IllegalArgumentException("List must not null or empty.");
        }

        int gcd = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            gcd = computeGreatestCommonDivisor(gcd, list.get(i));
        }
        return gcd;
    }

    public Server next() {
        while (true) {
            currentIndex = (currentIndex + 1) % serverCount;
            if (currentIndex == 0) {
                currentWeight = currentWeight - greatestCommonDivisor;
                if (currentWeight <= 0 ) {
                    currentWeight = maximumWeight;
                    if (currentWeight == 0) {
                        return null;
                    }
                }
            }
            Server server = servers.get(currentIndex);
            if (server.weight >= currentWeight) {
                return server;
            }
        }
    }

    public static void main(String[] args) {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("192.168.0.1", 1));
        servers.add(new Server("192.168.0.2", 2));
        servers.add(new Server("192.168.0.3", 4));
        servers.add(new Server("192.168.0.4", 8));
        // weight round robin
        WeightRoundRobin wrr = new WeightRoundRobin(servers);
        for (int i = 0; i < 100; i++) {
            System.out.println(wrr.next());
        }
    }
}

