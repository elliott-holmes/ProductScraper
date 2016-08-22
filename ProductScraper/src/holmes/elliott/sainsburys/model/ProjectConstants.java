/**
 * Just a helper class for some constants (referenced as statics) 
 * to prevent them being created multiple times. 
 * @author holmese
 *
 */
package holmes.elliott.sainsburys.model;

import java.math.BigDecimal;

public class ProjectConstants {
	// Private Constructor to prevent Instantiation
	private ProjectConstants() {
	};

	public static final BigDecimal ONE_THOUSAND = new BigDecimal("1000");
	public static final String STRING_BLANK = "";
	public static final String QUOTES = "\"";
	public static final String JSON_START = "{";
	public static final String JSON_END = "}";
	public static final String JSON_SEP = ",";
	public static final String JSON_PAIR = ": ";

}
