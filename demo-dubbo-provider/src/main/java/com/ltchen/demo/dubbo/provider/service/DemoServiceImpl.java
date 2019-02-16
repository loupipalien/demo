package com.ltchen.demo.dubbo.provider.service;

import com.alibaba.dubbo.rpc.RpcContext;
import com.ltchen.demo.dubbo.api.service.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service("demoServiceImpl")
public class DemoServiceImpl implements DemoService, InitializingBean, ApplicationContextAware, ApplicationListener {

    private static final Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);
	@Override
	public String sayHello(String name) {
        System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response form provider: " + RpcContext.getContext().getLocalAddress();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("===================ApplicationContextAware");
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            logger.info("=== {}", ((ContextRefreshedEvent) applicationEvent).getApplicationContext().getParent());
            logger.info("========================ApplicationListener");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("=========================InitializingBean");
    }
}
