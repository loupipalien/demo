package com.ltchen.demo.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltchen on 2019/1/9.
 * 平滑的加权轮询调度算法
 */
public class SmoothWeightRoundRobin {
    public static class Server {
        private String address;
        private int weight;
        private int currentWeight;

        public Server(String address, int weight) {
            this.address = address;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "Server{address='" + address + ", weight=" + weight + ", currentWeight=" + currentWeight + '}';
        }
    }

    private List<Server> servers;
    private int totalWeight;

    public SmoothWeightRoundRobin(List<Server> servers) {
        this.servers = servers;
        totalWeight = servers.stream().map(server -> server.weight).reduce((a,b) -> a + b).get();
    }

    public Server next() {
        Server best = null;
        for (Server server : servers) {
            server.currentWeight += server.weight;
            if (best == null || best.currentWeight < server.currentWeight) {
                best = server;
            }
        }
        if (best != null) {
            best.currentWeight -= totalWeight;
        }
        return best;
    }

    public static void main(String[] args) {
        List<Server> servers = new ArrayList<>();
        servers.add(new Server("192.168.0.1", 1));
        servers.add(new Server("192.168.0.2", 2));
        servers.add(new Server("192.168.0.3", 4));
        servers.add(new Server("192.168.0.4", 8));
        // smooth weight round robin
        SmoothWeightRoundRobin swrr = new SmoothWeightRoundRobin(servers);
        for (int i = 0; i < 100; i++) {
            System.out.println(swrr.next());
        }
    }

}

