package com.ltchen.demo.hamcrest.tutorial;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static com.ltchen.demo.hamcrest.tutorial.IsNotANumber.*;

import junit.framework.TestCase;

public class NumberTest extends TestCase {

	public void testSquareRootOfMinusOneIsNotANumber() {
		assertThat(Math.sqrt(-1), is(notANumber()));
	}
}
