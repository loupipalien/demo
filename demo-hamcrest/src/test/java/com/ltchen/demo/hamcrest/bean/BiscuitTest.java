package com.ltchen.demo.hamcrest.bean;

import junit.framework.TestCase;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.*;

import com.ltchen.demo.hamcrest.bean.Biscuit;

public class BiscuitTest extends TestCase {
	
	public void testEquals(){ 
		Biscuit theBiscuit = new Biscuit("Ginger"); 
		Biscuit myBiscuit = new Biscuit("Ginger");
		assertThat(theBiscuit, equalTo(myBiscuit)); 
		//可添加测试的标识符
		assertThat("chocolate chips", theBiscuit.getChocolateChipCount(), equalTo(10)); 
		assertThat("hazelnuts", theBiscuit.getHazelnutCount(), equalTo(3));
		//以下断言都是等价的
		assertThat(theBiscuit, equalTo(myBiscuit)); 
		assertThat(theBiscuit, is(equalTo(myBiscuit))); 
		assertThat(theBiscuit, is(myBiscuit));
	} 
	
}