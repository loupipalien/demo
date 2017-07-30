package com.ltchen.demo.spring.mvc.controller;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ltchen.demo.spring.mvc.bean.Spitter;
import com.ltchen.demo.spring.mvc.bean.SpitterRepository;
import com.ltchen.demo.spring.mvc.bean.SpittleRepository;


@SuppressWarnings("unused")
public class SpitterControllerTest {

	@Test
	public void testRegister() throws Exception {
		
		SpitterRepository mockRepository = Mockito.mock(SpitterRepository.class);
		Spitter unsaved = new Spitter("loupipalien", "123456", "Chen", "LT", "loupipalien@gmail.com");
		Spitter saved = new Spitter(24L,"loupipalien", "123456", "Chen", "LT", "loupipalien@gmail.com");
		Mockito.when(mockRepository.save(unsaved)).thenReturn(saved);
		
		SpitterController spitterController = new SpitterController(mockRepository);
		// mock SpringMVC
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spitterController).build();
		//执行并断言期望的值
		mockMvc.perform(MockMvcRequestBuilders.post("/spitter/register")
				.param("username", "loupipalien")
				.param("password", "123456")
				.param("firstName", "Chen")
				.param("lastName", "LT")
				.param("email", "loupipalien@gmail.com"))
				.andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/loupipalien"));
		//校验保存情况
		Mockito.verify(mockRepository, Mockito.atLeastOnce()).save(unsaved);
		
	}

}
