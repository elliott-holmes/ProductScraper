package holmes.elliott.productreader.tests;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import holmes.elliott.productreader.model.Product;
import holmes.elliott.productreader.model.Response;

public class ResponseTest {

	@Test
	public void testToString() {
		Product product = new Product();
		String expectedResponse = "{\"results\": [{\"title\": \"Sainsbury's Avocado, Ripe & Ready x2\",\"size\": \"90.6kb\",\"unit_price\": 1.80,\"description\": \"Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...\"}, {\"title\": \"Sainsbury's Avocado, Ripe & Ready x4\",\"size\": \"87kb\",\"unit_price\": 2.00,\"description\": \"Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...\"}],\"total\": 3.80}";
		Response response = new Response();
		product.setDescription("Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...");
		product.setSize("90.6kb");
		product.setTitle("Sainsbury's Avocado, Ripe & Ready x2");
		product.setUnit_price(new BigDecimal("1.80"));
		response.addResult(product);
		product = new Product();
		product.setDescription("Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...");
		product.setSize("87kb");
		product.setTitle("Sainsbury's Avocado, Ripe & Ready x4");
		product.setUnit_price(new BigDecimal("2.00"));
		response.addResult(product);
		assertEquals(response.toString(), expectedResponse);

		
	}

}
