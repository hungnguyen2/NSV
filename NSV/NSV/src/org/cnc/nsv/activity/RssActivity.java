package org.cnc.nsv.activity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.developerworks.android.FeedParser;
import org.developerworks.android.FeedParserFactory;
import org.developerworks.android.Message;
import org.developerworks.android.ParserType;
import org.xmlpull.v1.XmlSerializer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class RssActivity extends Activity implements Constant {

	TextView tvTitle;
	ImageView ivBack;
	List<Message> messages;
	WebView wvContent;
	String feedUrl;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rss);

		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);
		
		tvTitle = (TextView) findViewById(R.id.tvTitle);
		ivBack = (ImageView) findViewById(R.id.button_back);
		
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		wvContent = (WebView) findViewById(R.id.wvContent);
		wvContent.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}
		});
		String openActivity = getIntent().getStringExtra(Constant.OPEN_ACTIVITY);
		if (openActivity != null) {
			if (openActivity.equals(KEY_RSS_NEWS)) {
				tvTitle.setText(R.string.news_feed);
			}
			if (openActivity.equals(KEY_RSS_GAME)) {
				tvTitle.setText(R.string.game);
			}
			feedUrl = getIntent().getStringExtra(Constant.KEY_URL);
			new  LoadDataTask().execute();
		}
	}

	class LoadDataTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(RssActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			loadFeed(ParserType.ANDROID_SAX, feedUrl);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}
	
	private void loadFeed(ParserType type, String feedUrl){
    	try{
	    	FeedParser parser = FeedParserFactory.getParser(type, feedUrl);
	    	long start = System.currentTimeMillis();
	    	messages = parser.parse();
	    	long duration = System.currentTimeMillis() - start;
	    	Log.i("AndroidNews", "Parser duration=" + duration);
	    	String xml = writeXml();
	    	Log.i("AndroidNews", xml);
	    	List<String> titles = new ArrayList<String>(messages.size());
	    	String html = "<html><head><meta charset=\"utf-8\" />" +
	    			"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head>" +
	    			"<body style=\"text-align:justify;color:#222;margin:0;padding:10px;line-height:normal\">" +
	    			"<style type=\"text/css\">" +
	    			"a {color: #333;text-decoration:underline;} h2 a {color: #005074;text-decoration:none;} h2 {margin: 15px 0 0 0;font-size: 1.4em;font-weight:normal} " +
	    			"ul, li {list-style:none;margin: 5px 0;padding:0;clear:both} " +
	    			"em {color: #666;font-style:normal;font-size:0.9em;} img {float:left; margin: 0 5px 0 0;}" +
	    			"</style>" +
	    			"<ul>";
	    	for (Message msg : messages){
	    		titles.add(msg.getTitle());
	    		html += "<li>" 
    				+ "<h2><a href=\"" + msg.getLink() + "\">" + msg.getTitle() + "</a></h2>"
    				+ "<div style=\"padding:3px 0;\"><em>" + msg.getDate()  + "</em></div>"
    				+ msg.getDescription()
    				+ "</li>";
	    	}
	    	html +="</ul></body></html>";
	    	wvContent.loadData(html, "text/html;charset=utf-8", "utf-8");
    	} catch (Throwable t){
    		Log.e("AndroidNews",t.getMessage(),t);
    	}
    }
    
	private String writeXml(){
		XmlSerializer serializer = Xml.newSerializer();
		StringWriter writer = new StringWriter();
		try {
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "messages");
			serializer.attribute("", "number", String.valueOf(messages.size()));
			for (Message msg: messages){
				serializer.startTag("", "message");
				serializer.attribute("", "date", msg.getDate());
				serializer.startTag("", "title");
				serializer.text(msg.getTitle());
				serializer.endTag("", "title");
				serializer.startTag("", "url");
				serializer.text(msg.getLink().toExternalForm());
				serializer.endTag("", "url");
				serializer.startTag("", "body");
				serializer.text(msg.getDescription());
				serializer.endTag("", "body");
				serializer.endTag("", "message");
			}
			serializer.endTag("", "messages");
			serializer.endDocument();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
