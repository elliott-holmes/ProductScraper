/**
 * An object to return the response size for a product URL 
 * and the description from the field within the linked document
 */
package holmes.elliott.productreader.model;

import java.math.BigDecimal;

public class URLResponseObject {
	private BigDecimal responseSize;
	private String description;

	/**
	 * @return the responseSize
	 */
	public BigDecimal getResponseSize() {
		return responseSize;
	}

	/**
	 * @param responseSize
	 *            the responseSize to set
	 */
	public void setResponseSize(BigDecimal responseSize) {
		this.responseSize = responseSize;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
