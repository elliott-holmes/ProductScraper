package holmes.elliott.sainsburys.testsuite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.Test;

import holmes.elliott.sainsburys.controller.URLController;

public class URLTester {

	@Test
	public void testValidateURL() {
		URLController controller = new URLController();
		assertTrue("URL is Valid", controller.validateURL("http://www.google.com"));
		assertFalse("URL is not Valid", controller.validateURL("ftp://www.google.com"));
	}

	@Test
	public void testGetURL() {
		try {
			URL url = new URL("http://www.sainsburys.co.uk");
			HttpURLConnection testConnection = (HttpURLConnection) url.openConnection();
			URLController controller = new URLController();
			HttpURLConnection response = controller.getURL("http://www.sainsburys.co.uk");
			assertEquals("Response Codes Match", testConnection.getResponseCode(), response.getResponseCode());
			assertEquals("Content Length matches", testConnection.getContentLengthLong(),
					response.getContentLengthLong());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unable to run test due to connection issue");
		}

	}

}
