package holmes.elliott.productreader.controller;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLController {
	
	/**
	 * Method to check if passed string is valid http(s) URL.
	 * 
	 * @param @String url - URL To validate
	 * @return @boolean - If the URL is a http/https URL.
	 */	
	public boolean validateURL (String url){
		
		Pattern pattern = Pattern.compile("^(http|https):\\/\\/.*");
		Matcher matcher = pattern.matcher(url);
		return matcher.matches();
	}

	/**
	 * Simple Helper method to check the validity of a URL and connection to that URL
	 * 
	 * @param @String urlToLoad - The url to check and return a connection object for
	 * @return @HttpURLConnection - a valid http URL Connection
	 */
	public HttpURLConnection getURL(String urlToLoad) {
		try{
			URL url = new URL(urlToLoad);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			if (urlConnection.getResponseCode()>=400){
				System.err.println("Error connecting to URL. PLease check the URL and try again");
				urlConnection = null;
			}
			return urlConnection;
			
		}catch (MalformedURLException mfe){
			System.err.println("Error loading URL. PLease check the URL syntax and try again");
		} catch (IOException e) {
			System.err.println("Error connecting to URL. Please check url is correct and you have a working connection.");
		}
		return null;
	}

}
