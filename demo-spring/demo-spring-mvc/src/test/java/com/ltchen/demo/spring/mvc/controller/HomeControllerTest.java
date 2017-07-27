package com.ltchen.demo.spring.mvc.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SuppressWarnings("unused")
public class HomeControllerTest {

	@Test
	public void testHome() throws Exception{
		HomeController controller = new HomeController();
		/**
		 * MockMvc模拟了对"/"发起GET请求,
		 * 并断言返回的视图名称为home,但并不真正的检查视图是否存在
		 */
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	    mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(MockMvcResultMatchers.view().name("home"));
	}
	
//	@Test
//	public void testHome() {
//		HomeController controller = new HomeController();
//		/*
//		 * 测试并没有断言当接受到对"/"的GET请求时会调用home()方法,
//		 * 也没有真正判断home就是视图的名称
//		 */
//		assertEquals("home", controller.home());
//	}

}
