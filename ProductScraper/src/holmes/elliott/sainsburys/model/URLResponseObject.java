/**
 * An object to return the response size and description for a product URL 
 * and the description from the field within the linked document
 */
package holmes.elliott.sainsburys.model;

import java.math.BigDecimal;

public class URLResponseObject {
	private BigDecimal responseSize;
	private String description;

	/**
	 * @return the responseSize
	 */
	public BigDecimal getResponseSize() {
		if (responseSize == null) {
			responseSize = BigDecimal.ZERO;
		}
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
