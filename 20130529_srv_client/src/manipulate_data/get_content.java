package manipulate_data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;


public class get_content {

	private static String URL;
	private static int port;
	private static String scheme;
	private static String userName;
	private static String password;
	private static String restRequest;

	/**
	 * 
	 * @param Url 
	 * @param Port
	 * @param Scheme
	 * @param UserName
	 * @param Pass
	 * @param RestRequest
	 * @return 
	 */
	public static String response_text(String Url, int Port, String Scheme,
			String UserName, String Pass, String RestRequest) {

		URL = Url;
		port = Port;
		scheme = Scheme;
		userName = UserName;
		password = Pass;
		restRequest = "/rest/" + RestRequest;
		String returnString= "";
		
		HttpHost targetHost = new HttpHost(URL, port, scheme);
		DefaultHttpClient httpclient = new DefaultHttpClient();

		httpclient.getCredentialsProvider().setCredentials(
				new AuthScope(URL, port),
				new UsernamePasswordCredentials(userName, password));

		// Create AuthCache instance
		AuthCache authCache = new BasicAuthCache();
		// Generate DIGEST scheme object, initialize it and add it to the local
		// auth cache
		// Generate DIGEST scheme object, initialize it and add it to the local
		// auth cache
		DigestScheme digestAuth = new DigestScheme();
		// Suppose we already know the realm name
		digestAuth.overrideParamter("realm", "some realm");
		// Suppose we already know the expected nonce value
		digestAuth.overrideParamter("nonce", "whatever");
		authCache.put(targetHost, digestAuth);

		// Add AuthCache to the execution context
		BasicHttpContext localcontext = new BasicHttpContext();
		localcontext.setAttribute(ClientContext.AUTH_CACHE, authCache);

		HttpGet httpget = new HttpGet(restRequest);

		
		try {
			HttpResponse response = null;
			try {
				response = httpclient.execute(targetHost, httpget,
						localcontext);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HttpEntity entity = response.getEntity();			
			StringBuilder sb = new StringBuilder();
			try {
			    BufferedReader reader = 
			           new BufferedReader(new InputStreamReader(entity.getContent()), 65728);
			    String line = null;

			    while ((line = reader.readLine()) != null) {
			        sb.append(line);
			    }
			}
			catch (IOException e) { e.printStackTrace(); }
			catch (Exception e) { e.printStackTrace(); }
			returnString +=  sb.toString();
		} finally {
			httpclient.getConnectionManager().shutdown();
		}
		return returnString;
	}
}
