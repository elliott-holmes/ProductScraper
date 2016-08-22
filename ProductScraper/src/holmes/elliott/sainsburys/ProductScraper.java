/**
 * Main product class to run the code.
 */
package holmes.elliott.sainsburys;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.Calendar;
import java.util.stream.Stream;

import holmes.elliott.sainsburys.controller.ProductController;
import holmes.elliott.sainsburys.controller.URLController;
import holmes.elliott.sainsburys.model.Response;

/**
 * @author holmese
 *
 */
public class ProductScraper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Check the URL we have been passed in the first place and that it is a
		// valid URL
		long startTime = Calendar.getInstance().getTimeInMillis();
		URLController urlController = new URLController();
		if (args.length == 0 || !urlController.validateURL(args[0])) {
			System.out.println("No valid URL specified as primary argument");
			return;
		}
		HttpURLConnection mainPage = urlController.getURL(args[0]);
		if (mainPage == null) {
			// Could not load URL but response should have already explained
			// cause so just terminate
			return;
		}
		// Now assuming all ok with the URL, parse through the returned HTML
		// until we reach the tag we are looking for (Initially a ul with a
		// class of productLister and Listview)
		ProductController productController = new ProductController();
		productController.setResponse(new Response());
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(mainPage.getInputStream()))) {
			Stream<String> line = reader.lines();
			line.forEach(productController::handleStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(productController.getResponse());
		long endTime = Calendar.getInstance().getTimeInMillis();
		
	}

}
