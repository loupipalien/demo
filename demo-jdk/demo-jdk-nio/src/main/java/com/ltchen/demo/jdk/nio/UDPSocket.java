package com.ltchen.demo.jdk.nio;

import java.io.IOException;
import java.net.*;

/**
 * Created by ltchen.
 * Refer: https://juejin.im/post/5ae199f3f265da0b8c24ab4f
 * Desc: 一处阻塞, (客户端和服务端) 接收时阻塞
 */
public class UDPSocket {

    public static void main(String[] args) {
        new Thread(new ServerThread()).start();
        new Thread(new ClientThread()).start();
    }

    public static class ClientThread implements Runnable {

        @Override
        public void run() {
            // 1. 创建 datagram socket, 用于接受发送 UDP 协议消息
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(1000); // 设置 socket 超时, 避免 server 未响应时阻塞
                for (int i = 0; i < 10; i++) {
                    String message = "I'm " + i + "-th message from client";
                    // 2. 创建 UDP 的发送包和接受包
                    DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, InetAddress.getLocalHost(), 10222);
                    DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                    boolean receiveResponse = false;
                    for (int j = 0; j < 5; j++) {
                        // 3. 发送
                        socket.send(sendPacket);
                        // 4. 接受服务器响应 (未接受到会阻塞, 直到超时或抛出异常)
                        socket.receive(receivePacket);
                        if (!receivePacket.getAddress().equals(sendPacket.getAddress())) {
                            throw new IOException("Receive packet from an unknown source.");
                        } else {
                            receiveResponse = true;
                            System.out.println("[Client]: " + new String(receivePacket.getData()));
                            break;
                        }
                    }
                    if (receiveResponse == false) {
                        System.out.println("No response from server, give up waiting.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServerThread implements Runnable {

        @Override
        public void run() {
            // 1. 创建 datagram socket, 用于接受发送 UDP 协议消息
            try (DatagramSocket socket = new DatagramSocket(10222)) {
                while (true) {
                    // 2. 接受包
                    DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(receivePacket);
                    System.out.println("[Server]: " + new String(receivePacket.getData()));
                    // 3. 返回响应
                    String message = "Server Response: " + new String(receivePacket.getData());
                    DatagramPacket sendPacket = new DatagramPacket(message.getBytes(), message.getBytes().length, receivePacket.getSocketAddress());
                    socket.send(sendPacket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
