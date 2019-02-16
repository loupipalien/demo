package com.ltchen.demo.spring.custom.xml;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by ltchen on 2018/11/22.
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("dateFormat", new SimpleDateFormatBeanDefinitionParser());
    }
}
