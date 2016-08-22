package holmes.elliott.sainsburys.testsuite;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import holmes.elliott.sainsburys.model.Product;

public class ProductTest {

	@Test
	public void testOutput() {
		Product product = new Product();
		String expectedResponse = "{\"title\": \"Sainsbury's Avocado, Ripe & Ready x2\",\"size\": \"90.6kb\",\"unit_price\": 1.80,\"description\": \"Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...\"}";
		product.setDescription(
				"Great to eat now - refrigerate at home 1 of 5 a day 1 avocado counts as 1 of your 5...");
		product.setSize("90.6kb");
		product.setTitle("Sainsbury's Avocado, Ripe & Ready x2");
		product.setUnit_price(new BigDecimal("1.80"));
		System.out.println(product.toString());
		System.out.println(expectedResponse);
		assertEquals(product.toString(), expectedResponse);

	}

}
