package com.ltchen.demo.hamcrest.tutorial;

public class Matchers {
	
	public static <T> org.hamcrest.Matcher<T> is(T param1) { return org.hamcrest.core.Is.is(param1); }

	@SuppressWarnings("deprecation")
	public static <T> org.hamcrest.Matcher<T> is(java.lang.Class<T> param1) { return org.hamcrest.core.Is.is(param1); }

	public static <T> org.hamcrest.Matcher<T> is(org.hamcrest.Matcher<T> param1) { return org.hamcrest.core.Is.is(param1); }

	public static <T> org.hamcrest.Matcher<Double> notANumber() { return com.ltchen.demo.hamcrest.tutorial.IsNotANumber.notANumber(); }
}
