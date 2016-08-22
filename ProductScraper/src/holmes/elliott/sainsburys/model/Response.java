/**
 * Object to represent the expected results from the stream
 */
package holmes.elliott.sainsburys.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedList;

public class Response {
	private LinkedList<Product> results;
	private BigDecimal total = BigDecimal.ZERO;

	/**
	 * @return the results
	 */
	public LinkedList<Product> getResults() {
		if (results == null) {
			results = new LinkedList<Product>();
		}
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(LinkedList<Product> results) {
		this.results = results;
	}

	public void addResult(Product result) {
		getResults().add(result);
		total = total.add(result.getUnit_price());
	}

	/**
	 * @return the total
	 */
	public BigDecimal getTotal() {
		BigDecimal newTotal = BigDecimal.ZERO;
		results.forEach(result -> newTotal.add(result.getUnit_price()));
		return newTotal;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder(ProjectConstants.JSON_START);
		for (Field field : this.getClass().getDeclaredFields()) {
			if (first) {
				first = false;
			} else {
				sb.append(ProjectConstants.JSON_SEP);
			}
			sb.append(ProjectConstants.QUOTES);
			sb.append(field.getName());
			sb.append(ProjectConstants.QUOTES);
			sb.append(ProjectConstants.JSON_SEP);
			try {
				if (field.getType().getName().equals(LinkedList.class.getName()) || field.getType().getName().equals(BigDecimal.class.getName())) {
					sb.append(field.get(this));
				} else {
					sb.append(ProjectConstants.QUOTES);
					sb.append(field.get(this));
					sb.append(ProjectConstants.QUOTES);
				}
			} catch (Exception e) {
				sb.append(ProjectConstants.STRING_BLANK);
			}
		}
		sb.append(ProjectConstants.JSON_END);
		return sb.toString();
	}
}
