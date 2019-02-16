package com.ltchen.demo.rpc.provider.skeleton;

import com.ltchen.demo.rpc.provider.app.Calculate;
import com.ltchen.demo.rpc.provider.app.CalculateImpl;
import com.ltchen.demo.rpc.request.CalculateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ltchen on 2018/12/4.
 */
public class CalculateSkeleton {

    private static final Logger logger = LoggerFactory.getLogger(CalculateSkeleton.class);
    private static final int PORT = 9090;

    private Calculate calculate = new CalculateImpl();

    public void run() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                try {
                    // 将请求反序列化
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    Object object = in.readObject();
                    logger.info("request is {}", object);

                    // 调用服务
                    int result = 0;
                    if (object instanceof CalculateRequest) {
                        CalculateRequest request = (CalculateRequest) object;
                        if ("add".equals(request.getMethod())) {
                            result = calculate.add(request.getA(), request.getB());
                        } else {
                            throw new UnsupportedOperationException("unsupported operation: " + request.getMethod());
                        }
                    } else {
                        throw new IllegalArgumentException("request must be CalculateRequest instance.");
                    }

                    // 返回结果
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(result);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    socket.close();
                }
            }
        } catch (Exception e) {
            serverSocket.close();
        }
    }
}
