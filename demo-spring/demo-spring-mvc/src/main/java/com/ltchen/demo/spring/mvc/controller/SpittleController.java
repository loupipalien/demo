package com.ltchen.demo.spring.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ltchen.demo.spring.mvc.bean.Spittle;
import com.ltchen.demo.spring.mvc.bean.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

//	private static final String MAX_LONG_AS_STRING = Long.toString(Long.MAX_VALUE);
	private static final String MAX_LONG_AS_STRING = "9223372036854775807";
	
	private SpittleRepository spittleRepository;
	
	@Autowired
	public SpittleController(SpittleRepository spittleRepository){
		this.spittleRepository = spittleRepository;
	}
	
//	@RequestMapping(method=RequestMethod.GET)
//	public String spittles(Model model){
//		//将spittle添加到模型中,当不指定key时会依据值的对象类型推断判定
//		model.addAttribute(spittleRepository.findSpittles(Integer.MAX_VALUE, 20));
//		//返回视图名
//		return "spittles";
//	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Spittle> spittles(@RequestParam(value="max",defaultValue=MAX_LONG_AS_STRING) long max,
			@RequestParam(value="count",defaultValue="20") int count){//通过查询参数接受输入
		//当处理器方法返回对象或集合时,这个值默认会放在模型中,模型的key会根据其类型推断出;视图逻辑名会根据请求路径推断出,视图名称将会是请求路径去掉"/",也就是spittles
		return spittleRepository.findSpittles(max, count);
	}
	
	@RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
	public String spittle(@PathVariable("spittleId") long spittleId,Model model){//@PathVariable注解没有value时会假定占位符的名称与方法的参数名相同
		model.addAttribute(spittleRepository.findOne(spittleId));
		return "spittle";
	}
}
