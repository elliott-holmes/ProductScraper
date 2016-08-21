/**
 * 
 */
package holmes.elliott.productreader;

import java.net.HttpURLConnection;

import holmes.elliott.productreader.controller.URLController;

/**
 * @author holmese
 *
 */
public class ProductReader {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Check the URL we have been passed in the first place and that it is a valid URL
		URLController urlController = new URLController();
		if (args.length == 0 || !urlController.validateURL(args[0])){
			System.out.println("No valid URL specified as primary argument");
			return;
		}
		HttpURLConnection mainPage = urlController.getURL(args[0]);
		if(mainPage==null){
			//Could not load URL but response should have already explained cause so just terminate
			return;
		}
		//Now assuming all ok with the URL, parse through the returned HTML until we reach the tag we are looking for (Initially a ul with a class of productLister and Listview)
		

	}

}
