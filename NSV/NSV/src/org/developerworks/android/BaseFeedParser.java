package org.developerworks.android;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public abstract class BaseFeedParser implements FeedParser {

	// names of the XML tags
	static final String CHANNEL = "channel";
	static final String PUB_DATE = "pubDate";
	static final  String DESCRIPTION = "description";
	static final  String LINK = "link";
	static final  String TITLE = "title";
	static final  String ITEM = "item";
	
	private final String feedUrl;

	protected BaseFeedParser(String feedUrl){
		this.feedUrl = feedUrl;
	}

	protected InputStream getInputStream() {
		try {
			HttpContext localContext = new BasicHttpContext();
			HttpClient mClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(feedUrl);
			HttpResponse response = mClient.execute(httpGet, localContext);
			HttpEntity entity = response.getEntity();
			return entity.getContent();
			// return feedUrl.openConnection().getInputStream();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}