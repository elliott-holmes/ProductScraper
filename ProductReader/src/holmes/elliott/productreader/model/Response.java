/**
 * Object to represent the expected results from the stream
 */
package holmes.elliott.productreader.model;

import java.math.BigDecimal;
import java.util.List;

public class Response implements Json {
	private List<Product> results;
	private BigDecimal total;

	/**
	 * @return the results
	 */
	public List<Product> getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(List<Product> results) {
		this.results = results;
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
