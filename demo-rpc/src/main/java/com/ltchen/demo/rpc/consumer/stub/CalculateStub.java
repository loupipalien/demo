package com.ltchen.demo.rpc.consumer.stub;

import com.ltchen.demo.rpc.provider.app.Calculate;
import com.ltchen.demo.rpc.request.CalculateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ltchen on 2018/12/4.
 */
public class CalculateStub implements Calculate{

    private static final Logger logger = LoggerFactory.getLogger(CalculateStub.class);
    public static final int PORT = 9090;


    @Override
    public int add(int a, int b) {
        List<String> providers = lookupProviders("Calculate.add");
        String provider = chooseProvider(providers);

        try {
            Socket socket = new Socket(provider, PORT);

            // 将请求序列化
            CalculateRequest request = generateRequest(a, b);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // 将请求发给服务提供方
            out.writeObject(request);

            // 将响应体反序列化
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Object response = in.readObject();

            if (response instanceof Integer) {
                return (int) response;
            } else {
                throw new InternalError();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new InternalError();
        }
    }

    private CalculateRequest generateRequest(int a, int b) {
        CalculateRequest request = new CalculateRequest();
        request.setMethod("add");
        request.setA(a);
        request.setB(b);
        return request;
    }

    private String chooseProvider(List<String> providers) {
        if (providers == null || providers.isEmpty()) {
            throw new RuntimeException("this is no providers.");
        }
        return providers.get(0);
    }

    private static List<String> lookupProviders(String name) {
        List<String> providers = new ArrayList<>();
        providers.add("127.0.0.1");
        return providers;
    }
}
