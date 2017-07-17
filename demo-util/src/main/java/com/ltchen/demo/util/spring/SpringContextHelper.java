package com.ltchen.demo.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHelper implements ApplicationContextAware {
    private static ApplicationContext ac = null;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
          ac = applicationContext;
    }
    public static ApplicationContext getApplicationContext(){
          return ac;
    }
    public static Object getBean(String name) {
          return ac.getBean(name);
    }     
}