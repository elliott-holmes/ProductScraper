/**
 * Object to contain all the response properties for the product
 * 
 * @author holmese
 *
 */
package holmes.elliott.productreader.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.LinkedList;

public class Product {

	private String title;
	private String size;
	private BigDecimal unit_price;
	private String description;

	/**
	 * @return the unit_price
	 */
	public BigDecimal getUnit_price() {
		return unit_price;
	}

	/**
	 * @param unit_price
	 *            the unit_price to set
	 */
	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
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

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	public String toString() {
		boolean first = true;
		StringBuilder sb = new StringBuilder("{");
		for (Field field : this.getClass().getDeclaredFields()) {
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append(ProjectConstants.QUOTES);
			sb.append(field.getName());
			sb.append(ProjectConstants.QUOTES);
			sb.append(": ");
			try {
				if (field.getType().getName().equals(LinkedList.class.getName()) || field.getType().getName().equals(BigDecimal.class.getName())) {
					sb.append(field.get(this));
				} else {
					sb.append(ProjectConstants.QUOTES);
					sb.append(field.get(this));
					sb.append(ProjectConstants.QUOTES);
				}
			} catch (Exception e) {
				sb.append("");
			}
		}
		sb.append("}");
		return sb.toString();
	}
}
