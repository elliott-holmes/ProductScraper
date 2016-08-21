package holmes.elliott.productreader.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import holmes.elliott.productreader.controller.URLController;

public class URLTester {

	@Test
	public void testValidateURL() {
		URLController controller = new URLController();
		
		assertTrue("URL is Valid", controller.validateURL("http://www.google.com"));
		assertFalse("URL is not Valid", controller.validateURL("ftp://www.google.com"));
	}

	@Test
	public void testGetURL() {
		fail("Not yet implemented"); // TODO
	}

}
