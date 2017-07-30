package com.ltchen.demo.spring.mvc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.ltchen.demo.spring.mvc.bean.Spittle;
import com.ltchen.demo.spring.mvc.bean.SpittleRepository;

public class SpittleControllerTest {

//	@Test
//	public void testSpittles() throws Exception{
//		List<Spittle> expectedSpittles = createSpittleList(20);
//		//mock SpittleRepository
//		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
//		Mockito.when(mockRepository.findSpittles(Long.MAX_VALUE, 20)).thenReturn(expectedSpittles);
//		
//		SpittleController spittleController = new SpittleController(mockRepository);
//		// mock SpringMVC
//		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spittleController)
//						  .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
//						  .build();
//		//执行并断言期望的值
//		mockMvc.perform(MockMvcRequestBuilders.get("/spittles"))
//				.andExpect(MockMvcResultMatchers.view().name("spittles"))
//				.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
//				.andExpect(MockMvcResultMatchers.model().attribute("spittleList",Matchers.hasItems(expectedSpittles.toArray())));
//	}
	
	@Test
	public void testSpittles() throws Exception{
		List<Spittle> expectedSpittles = createSpittleList(50);
		//mock SpittleRepository
		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
		//预期的max和count参数
		Mockito.when(mockRepository.findSpittles(238900, 50)).thenReturn(expectedSpittles);
		
		SpittleController spittleController = new SpittleController(mockRepository);
		// mock SpringMVC
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spittleController)
						  .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
						  .build();
		//执行并断言期望的值
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles?max=238900&count=50"))
				.andExpect(MockMvcResultMatchers.view().name("spittles"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittleList"))
				.andExpect(MockMvcResultMatchers.model().attribute("spittleList",Matchers.hasItems(expectedSpittles.toArray())));
	}
	
	@Test
	public void testSpittle() throws Exception{
		Spittle expectedSpittle = new Spittle("Hello", new Date());
		//mock SpittleRepository
		SpittleRepository mockRepository = Mockito.mock(SpittleRepository.class);
		//预期的expectedSpittle
		Mockito.when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
		
		SpittleController spittleController = new SpittleController(mockRepository);
		// mock SpringMVC
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(spittleController)
						  .setSingleView(new InternalResourceView("/WEB-INF/views/spittle.jsp"))
						  .build();
		//执行并断言期望的值
		mockMvc.perform(MockMvcRequestBuilders.get("/spittles/12345"))
				.andExpect(MockMvcResultMatchers.view().name("spittle"))
				.andExpect(MockMvcResultMatchers.model().attributeExists("spittle"))
				.andExpect(MockMvcResultMatchers.model().attribute("spittle",expectedSpittle));
	}
	
	//创建Spittles
	private List<Spittle> createSpittleList(int count){
		List<Spittle> spittles = new ArrayList<Spittle>();
		for (int i = 0; i < count; i++) {
			spittles.add(new Spittle("Spittle " + i, new Date()));
		}
		return spittles;
	}
}
