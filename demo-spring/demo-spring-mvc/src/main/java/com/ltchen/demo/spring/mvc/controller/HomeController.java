package com.ltchen.demo.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller //声明为一个控制器
@RequestMapping(value="/")
public class HomeController {
	
	@RequestMapping(method=RequestMethod.GET) //处理对"/home"的GET的处理
	public String home(){
		//返回视图名
		return "home"; 
	}
}
