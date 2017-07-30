package com.ltchen.demo.hamcrest;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;


public class HamcrestMatchersTest {

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test  
	public void testHamcrestMatchers() {  
	    // 核心匹配  
	    // allOf: 所有条件都必须满足，相当于&&  
	    assertThat("myname", allOf(startsWith("my"), containsString("name")));  
	    // anyOf: 其中一个满足就通过， 相当于||  
	    assertThat("myname", anyOf(startsWith("na"), containsString("name")));  
	    // both: &&  
	    assertThat("myname", both(containsString("my")).and(containsString("me")));  
	    // either: 两者之一  
	    assertThat("myname", either(containsString("my")).or(containsString("you")));  
	    // everyItem: 每个元素都需满足特定条件  
	    assertThat(Arrays.asList("my", "mine"), everyItem(startsWith("m")));  
	    // hasItem: 是否有这个元素  
	    assertThat(Arrays.asList("my", "mine"), hasItem("my"));  
	    // hasItems: 包含多个元素  
	    assertThat(Arrays.asList("my", "mine", "your"), hasItems("your", "my"));  
	    // is: is(equalTo(x))或is(instanceOf(clazz.class))的简写  
	    assertThat("myname", is("myname"));  
	    assertThat("mynmae", is(String.class));  
	    // anything(): 任何情况下，都匹配正确  
	    assertThat("myname", anything());  
	    // not: 否为真，相当于！  
	    assertThat("myname", is(not("you")));  
	    // nullValue(): 值为空  
	    String str = null;  
	    assertThat(str, is(nullValue()));  
	    // notNullValue(): 值不为空  
	    String str2 = "123";  
	    assertThat(str2, is(notNullValue()));  
	  
	      
	    // 字符串匹配  
	    // containsString：包含字符串  
	    assertThat("myname", containsString("na"));  
	    // stringContainsInOrder: 顺序包含，“my”必须在“me”前面  
	    assertThat("myname", stringContainsInOrder(Arrays.asList("my", "me")));  
	    // endsWith: 后缀  
	    assertThat("myname", endsWith("me"));  
	    // startsWith: 前缀  
	    assertThat("myname", startsWith("my"));  
	    // isEmptyString(): 空字符串  
	    assertThat("", isEmptyString());  
	    // equalTo: 值相等， Object.equals(Object)  
	    assertThat("myname", equalTo("myname"));  
	    assertThat(new String[] {"a", "b"}, equalTo(new String[] {"a", "b"}));  
	    // equalToIgnoringCase: 比较时，忽略大小写  
	    assertThat("myname", equalToIgnoringCase("MYNAME"));  
	    // equalToIgnoringWhiteSpace: 比较时， 首尾空格忽略， 比较时中间用单个空格  
	    assertThat(" my \t name ", equalToIgnoringWhiteSpace(" my name "));  
	    // isOneOf: 是否为其中之一  
	    assertThat("myname", isOneOf("myname", "yourname"));  
	    // isIn: 是否为其成员  
	    assertThat("myname", isIn(new String[]{"myname", "yourname"}));  
	    // toString() 返回值校验  
	    assertThat(333, hasToString(equalTo("333")));  
	     
	      
	    // 数值匹配  
	    // closeTo: [operand-error, operand+error], Double或BigDecimal类型  
	    assertThat(3.14, closeTo(3, 0.5));  
	    assertThat(new BigDecimal("3.14"), is(closeTo(new BigDecimal("3"), new BigDecimal("0.5"))));  
	    // comparesEqualTo: compareTo比较值  
	    assertThat(2, comparesEqualTo(2));  
	    // greaterThan： 大于  
	    assertThat(2, greaterThan(0));  
	    // greaterThanOrEqualTo: 大于等于  
	    assertThat(2, greaterThanOrEqualTo(2));  
	    // lessThan: 小于  
	    assertThat(0, lessThan(2));  
	    // lessThanOrEqualTo: 小于等于  
	    assertThat(0, lessThanOrEqualTo(0));  
	      
	      
	      
	    // 集合匹配  
	    // array: 数组长度相等且对应元素也相等  
	    assertThat(new Integer[]{1, 2, 3}, is(array(equalTo(1), equalTo(2), equalTo(3))));  
	    // hasItemInArray: 数组是否包含特定元素  
	    assertThat(new String[]{"my", "you"}, hasItemInArray(startsWith("y")));  
	    // arrayContainingInAnyOrder， 顺序无关，长度要一致  
	    assertThat(new String[]{"my", "you"}, arrayContainingInAnyOrder("you", "my"));  
	    // arrayContaining:  顺序，长度一致  
	    assertThat(new String[]{"my", "you"}, arrayContaining("my", "you"));  
	    // arrayWithSize: 数组长度  
	    assertThat(new String[]{"my", "you"}, arrayWithSize(2));  
	    // emptyArray: 空数组  
	    assertThat(new String[0], emptyArray());  
	    // hasSize: 集合大小  
	    assertThat(Arrays.asList("my", "you"), hasSize(equalTo(2)));  
	    // empty: 空集合  
	    assertThat(new ArrayList<String>(), is(empty()));  
	    // isIn: 是否为集合成员  
	    assertThat("myname", isIn(Arrays.asList("myname", "yourname")));  
	    // Map匹配  
	    Map<String, String> myMap = new HashMap<String, String>();  
	    myMap.put("name", "john");  
	    // hasEntry: key && value匹配  
	    assertThat(myMap, hasEntry("name", "john"));  
	    // hasKey: key匹配  
	    assertThat(myMap, hasKey(equalTo("name")));  
	    // hasValue: value匹配  
	    assertThat(myMap, hasValue(equalTo("john")));  
	}  

}
