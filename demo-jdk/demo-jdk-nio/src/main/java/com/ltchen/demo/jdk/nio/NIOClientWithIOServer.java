package com.ltchen.demo.jdk.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen.
 */
public class NIOClientWithIOServer {

    public static void main(String[] args) {
        new Thread(new ServerThread()).start();
        new Thread(new ClientThread()).start();
    }

    /**
     * NIO Client
     */
    public static class ClientThread implements Runnable {
        @Override
        public void run() {
            try(SocketChannel channel = SocketChannel.open()) {
                ((SocketChannel) channel.configureBlocking(false)).connect(new InetSocketAddress("localhost", 10333));
                while (!channel.finishConnect()) { // 在非阻塞模式下, 未完成连接时会立即返回 false
                    System.out.print(".");
                }
                System.out.println("Connected to server...");
                // 发送消息
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                for (int i = 0; i < 10; i++) {
                    String message = "I'm " + i + "-th message from client";
                    buffer.put(message.getBytes());  // 写入 buffer
                    buffer.flip();
                    while (buffer.hasRemaining()) { // 需循环调用写入 channel, 因为 write 方法无法保证写入多少字节
                        channel.write(buffer);
                    }
                    buffer.clear();
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * IO Server
     */
    public static class ServerThread implements Runnable {
        @Override
        public void run() {
            try(ServerSocket serverSocket = new ServerSocket(10333)) {
                int size;
                byte[] buffer = new byte[1024];
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("Client socket: " + socket);
                    InputStream in = socket.getInputStream();
                    while ((size = in.read(buffer)) != -1) {
                        System.out.println(new String(Arrays.copyOf(buffer, size)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
