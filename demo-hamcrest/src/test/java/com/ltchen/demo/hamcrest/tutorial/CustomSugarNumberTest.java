package com.ltchen.demo.hamcrest.tutorial;

import static org.hamcrest.MatcherAssert.assertThat;

import static com.ltchen.demo.hamcrest.tutorial.Matchers.*;

import junit.framework.TestCase;

public class CustomSugarNumberTest extends TestCase {

	public void testSquareRootOfMinusOneIsNotANumber() {
		assertThat(Math.sqrt(-1), is(notANumber()));
	}

}
