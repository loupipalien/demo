package com.ltchen.demo.jdk.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by ltchen.
 * Refer: https://juejin.im/post/5ae148ca6fb9a07ab3792220
 * Desc: 两处阻塞, 客户端连接时阻塞, 服务端接受时阻塞
 * Other: 与 UDP 的比较
 * | ------- | TCP | UDP |
 * | 是否连接 | 面向连接 | 面向无连接 |
 * | 传输可靠性 | 是 | 否 |
 * | 应用场合 | 大量数据 | 少量数据 |
 * | 速度 | 慢 | 块 |
 */
public class TCPSocket {

    public static void main(String[] args) {
        new Thread(new ServerThread()).start();
        new Thread(new ClientThread()).start();
        new Thread(new ClientThread()).start();
    }

    public static class ClientThread implements Runnable {

        @Override
        public void run() {
            /**
             * Socket 的有参构造函数, 是阻塞式连接, 直到连接成功或者抛出异常
             * Socket 的无参构造函数, 后续需调用 connect() 方法建立连接, 可以设置超时时间, 避免长时间阻塞
             */
            try(Socket socket = new Socket("localhost", 10111)) {
                OutputStream out = socket.getOutputStream();
                for (int i = 0; i < 10; i++) {
                    String message = "I'm " + i + "-th message from client \n";
                    out.write(message.getBytes());
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ServerThread implements Runnable {
        private static ExecutorService executor = Executors.newFixedThreadPool(10);

        @Override
        public void run() {
            /**
             * ServerSocket 的无参构造函数, 后续需调用 bind() 方法绑定端口号 (或者限制队列长度)
             */
            try(ServerSocket serverSocket = new ServerSocket(10111)) {
                while (true) {
                    /**
                     * ServerSocket 的 accept() 的方法会阻塞当前线程, 直到与客户端建立连接或者抛出异常
                     */
                    Socket socket = serverSocket.accept();
                    executor.submit(new HandleThread(socket));  // 使用线程处理, 避免阻塞
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }

        /**
         * 处理线程
         */
        public static class HandleThread implements Runnable {
            private Socket socket;

            public HandleThread(Socket socket) {
                this.socket = socket;
            }

            @Override
            public void run() {
                // 为了方便处理, 使用了 Reader 来处理
                try(BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {  // 直到客户端 socket 关闭了流或者抛出了异常才会返回 null
                        System.out.println(socket + ": " + line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
