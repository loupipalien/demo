package com.ltchen.demo.spring.mvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcAppInitalizer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		//返回带有Configuration注解的类会用来配置ContextLoaderListener创建的应用上下文中的bean
		return new Class<?>[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		//返回带有Configuration注解的类会用来定义DispatcherServlet应用上下文中的bean
		return new Class<?>[] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		//将DispatcherServlet映射到"/"
		return new String[] {"/"};
	}

}
