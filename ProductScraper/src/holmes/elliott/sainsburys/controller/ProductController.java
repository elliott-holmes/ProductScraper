/**
 * Handles the management of the input stream and uses the stream to create products
 */
package holmes.elliott.sainsburys.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.stream.Stream;

import holmes.elliott.sainsburys.model.Product;
import holmes.elliott.sainsburys.model.ProjectConstants;
import holmes.elliott.sainsburys.model.Response;
import holmes.elliott.sainsburys.model.URLResponseObject;


public class ProductController {
	private boolean productMode = false;
	private boolean productsComplete = false;
	private boolean titleLine = false;
	private boolean priceLine = false;
	private Response response;
	private Product product;
	private URLResponseObject urlResponseObject;

	
	/**
	 * Method to handle each line of the HTML
	 * We could use a HTML parser and DOM traversing / search, but this can be slow
	 * and as we are going through each line of the HTML we may as well process as we go.
	 * 
	 * @param lineStr - The line of HTML
	 */
	public void handleStream(String lineStr) {
		//If we have the list of products, nothing else to do so just skip 
		if (!productsComplete) {
			if (productMode){				
				//we are in the product list
				if(lineStr.contains("<li>")) {
					//Start of a new Product
					product = new Product();
				}else if (lineStr.contains("</li>")) {
					//end of the product so add to the results object
					getResponse().addResult(product);
					product = null;
				}else if (lineStr.contains("<a href")) {
					//link to sub page of product
					String subProductUrl = lineStr.substring(lineStr.indexOf("<a href=") + 9, lineStr.lastIndexOf("\""));
					//Go get the details from the sub page					
					URLResponseObject productSubResponse = getSizeAndDescriptionforProduct(subProductUrl);
					if (productSubResponse != null) {
						product.setSize(productSubResponse.getResponseSize()
								.divide(ProjectConstants.ONE_THOUSAND, 0, BigDecimal.ROUND_HALF_UP).toString() + "kb");
						product.setDescription(productSubResponse.getDescription());
					}
					//and the next line is the title
					titleLine = true;
				}else if(titleLine){
					//get the title
					product.setTitle(lineStr.trim());
					titleLine = false;
				}else if(priceLine){
					//handle the price line (even though the &pound entity is not finished with a ;)
					BigDecimal unitPrice = new BigDecimal(lineStr.substring(6,lineStr.indexOf("<")));
					product.setUnit_price(unitPrice);;
					priceLine=false;
				}else if(lineStr.contains("<p") && lineStr.contains("pricePerUnit")){
					//The next line will contain the unit price details
					priceLine = true;
				}else if (lineStr.toLowerCase().contains("</ul>")) {
					//because we are in the products list we must have reached the end
					productMode = false;
					productsComplete = true;
				}
			}else if (lineStr.toLowerCase().contains("<ul")) {
				//checks we are in a list
				if (lineStr.toLowerCase().contains("productlister")) {
					//Check we are in the products list
					productMode = true;
				}
			}
		}
	}

	private boolean intoDescription;
	private boolean descriptionFound;

	public void handleSubStream(String lineStr) {
		if (!descriptionFound) {
			if (intoDescription) {
				//Process the description lines
				if (lineStr.toLowerCase().contains("</div>")) {
					intoDescription = false;
					descriptionFound = true;
				} else if (!lineStr.toLowerCase().contains("<div")) {
					//if the line is not a starting div then strip the HTML tags and put them into the description
					getUrlResponseObject().setDescription(getUrlResponseObject().getDescription() + cleanHtmlTags(lineStr));
				}
			}
			if (!intoDescription && lineStr.contains(">Description<")) {
				//We have found the description Heading
				intoDescription = true;
			}
		}

	}

	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}

	/**
	 * Private method to handle the stream of the sub page. 
	 * @param url
	 * @return @URLResponseObject Object containing page size and description
	 */
	private URLResponseObject getSizeAndDescriptionforProduct(String url) {
		URLController urlController = new URLController();
		HttpURLConnection subPage = urlController.getURL(url);
		//This should check we have the page
		if (subPage == null) {
			return null;
		}
		ProductController productController = new ProductController();
		URLResponseObject subResponse = new URLResponseObject();
		//Set the response size
		subResponse.setResponseSize(new BigDecimal(subPage.getContentLengthLong()));
		//Initialize the description
		subResponse.setDescription(ProjectConstants.STRING_BLANK);
		productController.setUrlResponseObject(subResponse);
		//parse the stream of the page to get the description
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(subPage.getInputStream()))) {
			Stream<String> line = reader.lines();
			line.forEach(productController::handleSubStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//and we are done with the sub page.
		return productController.getUrlResponseObject();
	}

	/**
	 * @return the urlResponseObject
	 */
	public URLResponseObject getUrlResponseObject() {
		return urlResponseObject;
	}

	/**
	 * @param urlResponseObject
	 *            the urlResponseObject to set
	 */
	public void setUrlResponseObject(URLResponseObject urlResponseObject) {
		this.urlResponseObject = urlResponseObject;
	}
	
	/**
	 * 
	 * Little helper method to remove any HTML tags
	 * 
	 * @param lineToClean
	 * @return @String clean text
	 */	
	private String cleanHtmlTags(String lineToClean){
		StringBuilder sb = new StringBuilder(lineToClean);
		int start = sb.indexOf("<");
		while (start > -1){
			sb.delete(start, sb.indexOf(">", start) + 1);
			start = sb.indexOf("<");			
		}
		return sb.toString();
	}

}
