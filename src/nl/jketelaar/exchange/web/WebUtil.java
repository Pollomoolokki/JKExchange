package nl.jketelaar.exchange.web;

import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * 
 * A WebUtil class fetches data from an URL
 * 
 * @author Everel, JKetelaar
 * 
 */
public class WebUtil {

	private static JSONParser jsonParser;

	private static String agent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	/**
	 * Agent to set at a URL connection
	 * 
	 * @param userAgent
	 */
	public static void setUserAgent(final String userAgent) {
		agent = userAgent;
	}

	/**
	 * Gets useragent
	 * 
	 * @return useragent
	 */
	public static String getUserAgent() {
		return agent;
	}

	/**
	 * Fetches content of a page
	 * 
	 * @param location
	 * @return contents of page
	 * @throws MalformedURLException
	 */
	public static String getContents(final String location)
			throws MalformedURLException {
		return getContents(new URL(location));
	}

	public static String getContents(final String location, String parameters) throws MalformedURLException {
		return getContents(new URL(location), parameters);
	}

	/**
	 * Get contents from URL
	 * 
	 * @param url
	 * @return page contents
	 */
	public static String getContents(final URL url) {
		return getContents(getConnection(url));
	}

	public static String getContents(final URL url, final String parameters) {
		return getContents(getConnection(url), parameters);
	}

	/**
	 * Gets contents from URLConnection
	 * 
	 * @param urlConnection
	 * @return page contents
	 */
	public static String getContents(URLConnection urlConnection) {
		try {
			final BufferedReader in = getReader(urlConnection);
			final StringBuilder builder = new StringBuilder();
			String line;
			if (in != null) {
				while ((line = in.readLine()) != null) {
                    builder.append(line);
                }
				in.close();
			}
			return builder.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public static String getContents(URLConnection urlConnection, String parameters) {
		try {
			urlConnection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(urlConnection.getOutputStream());
			wr.write(parameters);
			wr.flush();
			wr.close();

			final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			final StringBuilder builder = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets buffered reader from string url
	 * 
	 * @param url
	 * @return bufferedreader
	 */
	public static BufferedReader getReader(final String url) {
		try {
			return getReader(new URL(url));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets BufferedReader from URL
	 * 
	 * @param url
	 * @return BufferedReader from URL
	 */
	public static BufferedReader getReader(final URL url) {
		return getReader(getConnection(url));
	}

	public static BufferedReader getReader(final URLConnection urlConnection) {
		try {
			return new BufferedReader(new InputStreamReader(
					urlConnection.getInputStream()));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets inputstream from url
	 * 
	 * @param url
	 * @return inputstream from url
	 */
	public static InputStream getInputStream(final URL url) {
		final URLConnection con = getConnection(url);
		try {
			return con.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Opens a connection
	 *
	 * @param url
	 * @return URLConnection to URL
	 */
	public static URLConnection getConnection(final URL url) {
		try {
			final URLConnection con = url.openConnection();
			con.setRequestProperty("User-Agent", agent);
			return con;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}

	public static BufferedReader getReader(final URL url, String username, String password) {
		try {
			String data = URLEncoder.encode("username", "UTF-8") + "=" + username;
	        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;
			
			URLConnection connection = url.openConnection();
		
			connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
 
            return new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	public static URLConnection getConnection(final URL url, String username, String password) {
		try {
			String data = URLEncoder.encode("username", "UTF-8") + "=" + username;
	        data += "&" + URLEncoder.encode("password", "UTF-8") + "=" + password;
			
			URLConnection connection = url.openConnection();
		
			connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();
 
            return connection;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Converts file format to url format
	 * @param file
	 * @return url to file
	 */
	public static URL toURL(File file) {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONParser getJsonParser() {
		if (jsonParser == null){
			jsonParser = new JSONParser();
		}
		return jsonParser;
	}
}