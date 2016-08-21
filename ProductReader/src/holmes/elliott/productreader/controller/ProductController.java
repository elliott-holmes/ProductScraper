package holmes.elliott.productreader.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.util.stream.Stream;

import holmes.elliott.productreader.model.Product;
import holmes.elliott.productreader.model.ProjectConstants;
import holmes.elliott.productreader.model.Response;
import holmes.elliott.productreader.model.URLResponseObject;

public class ProductController {
	private boolean productMode = false;
	private boolean productsComplete = false;
	private boolean titleLine = false;
	private boolean priceLine = false;
	private Response response;
	private Product product;
	private URLResponseObject urlResponseObject;

	public void handleStream(String lineStr) {
		if (!productsComplete) {
			if (productMode){				
				if(lineStr.contains("<li>")) {
					product = new Product();
				}else if (lineStr.contains("</li>")) {
					getResponse().addResult(product);
					product = null;
				}else if (lineStr.contains("<a href")) {
					String subProductUrl = lineStr.substring(lineStr.indexOf("<a href=") + 9, lineStr.lastIndexOf("\""));
					URLResponseObject productSubResponse = getSizeAndDescriptionforProduct(subProductUrl);
					if (productSubResponse != null) {
						product.setSize(productSubResponse.getResponseSize()
								.divide(ProjectConstants.ONE_THOUSAND, 0, BigDecimal.ROUND_HALF_UP).toString() + "kb");
						product.setDescription(productSubResponse.getDescription());
					}
					//and the next line is the title
					titleLine = true;
				}else if(titleLine){
					product.setTitle(lineStr.trim());
					titleLine = false;
				}else if(priceLine){
					BigDecimal unitPrice = new BigDecimal(lineStr.substring(6,lineStr.indexOf("<")));
					product.setUnit_price(unitPrice);;
					priceLine=false;
				}else if(lineStr.contains("<p") && lineStr.contains("pricePerUnit")){
					priceLine = true;
				}else if (lineStr.toLowerCase().contains("</ul>")) {
					productMode = false;
					productsComplete = true;
				}
			}else if (lineStr.toLowerCase().contains("<ul")) {
				if (lineStr.toLowerCase().contains("productlister")) {
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
				if (lineStr.toLowerCase().contains("</div>")) {
					intoDescription = false;
					descriptionFound = true;
				} else if (lineStr.toLowerCase().contains("<div")) {
					// ignore this line
				} else {
					getUrlResponseObject().setDescription(getUrlResponseObject().getDescription() + cleanHtmlTags(lineStr));
				}
			}
			if (!intoDescription && lineStr.contains(">Description<")) {
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

	private URLResponseObject getSizeAndDescriptionforProduct(String url) {
		URLController urlController = new URLController();
		HttpURLConnection subPage = urlController.getURL(url);
		if (subPage == null) {
			return null;
		}
		ProductController productController = new ProductController();
		URLResponseObject subResponse = new URLResponseObject();
		subResponse.setResponseSize(new BigDecimal(subPage.getContentLengthLong()));
		subResponse.setDescription(ProjectConstants.STRING_BLANK);
		productController.setUrlResponseObject(subResponse);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(subPage.getInputStream()))) {
			Stream<String> line = reader.lines();
			line.forEach(productController::handleSubStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
