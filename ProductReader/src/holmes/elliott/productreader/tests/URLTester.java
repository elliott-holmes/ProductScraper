package holmes.elliott.productreader.tests;

import static org.junit.Assert.*;

import java.net.HttpURLConnection;
import java.net.URL;

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
		try{
			URL url = new URL("http://www.sainsburys.co.uk");
			HttpURLConnection testConnection = (HttpURLConnection)url.openConnection();
			URLController controller = new URLController();
			HttpURLConnection response = controller.getURL("http://www.sainsburys.co.uk");
			assertEquals("Response Codes Match", testConnection.getResponseCode(), response.getResponseCode());
			assertEquals("Content Length matches" , testConnection.getContentLengthLong(), response.getContentLengthLong());
		}catch (Exception e){
			e.printStackTrace();
			fail("Unable to run test due to connection issue");
		}
		
	}

}
