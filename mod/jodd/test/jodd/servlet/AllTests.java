// Copyright (c) 2003-2009, Jodd Team (jodd.org). All Rights Reserved.

package jodd.servlet;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests extends TestSuite {

	public AllTests() {
		super("jodd.servlet test suite");
		addTestSuite(HtmlEncoderTest.class);
		addTestSuite(HtmlTagTest.class);
	}

	public static Test suite() {
		return new AllTests();
	}

}