package org.cnc.nsv.news;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.store;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_detailnews extends Activity implements Constant{

	Ojnews ojnews;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailnews);
		new createmenu().create(Activity_detailnews.this);
		findViewById(R.id.menu_news).setSelected(true);
		ojnews = store.OJNEWS;
		
		final ImageView image=(ImageView) findViewById(R.id.detanews_image);
		
		WebView wvContent = (WebView) findViewById(R.id.wvContent);
		wvContent.setWebChromeClient(new WebChromeClient() {
			
		});
		wvContent.loadDataWithBaseURL(API_URL, ojnews.getDetail(), "text/html", "utf-8", null);
		
		TextView title =(TextView) findViewById(R.id.detanews_title);
		title.setText(R.string.news);
		((TextView) findViewById(R.id.tvNewsTitle)).setText(ojnews.getTitle());
		
		if(title.getText().toString().length()>maxtextlength) title.setText(title.getText().toString().substring(0, maxtextlength)+"...");
		ImageView button_back = (ImageView) findViewById(R.id.button_back);
		button_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		findViewById(R.id.btEmail).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_SEND);
				i.setType("message/rfc822");
				i.putExtra(Intent.EXTRA_EMAIL, new String [] {""});
				i.putExtra(Intent.EXTRA_SUBJECT, ojnews.getTitle());
				i.putExtra(Intent.EXTRA_TEXT, ojnews.getTitle() + "\n" +  ojnews.getSummary() + "\n" + ojnews.getUrl());
				startActivity(i);
			}
		});
		
		findViewById(R.id.btFacebook).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.FACEBOOK_SHARE + ojnews.getUrl()));
				startActivity(i);
			}
		});
		
		findViewById(R.id.btTwitter).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.TWITTER_SHARE + ojnews.getTitle() + " " + ojnews.getUrl()));
				startActivity(i);
			}
		});
		
		Handler loadImageHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1 && msg.obj != null) {
					try {
						image.setImageBitmap((Bitmap) msg.obj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		};
		
		String imageUrl = ojnews.getImageUrl();
		if (!imageUrl.equalsIgnoreCase("")) {
			new Utils.LoadImageTask(loadImageHandler).execute(imageUrl);
		}
	}
	
	void shareContent() {
		if (ojnews != null) {
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.putExtra(Intent.EXTRA_TEXT, ojnews.getUrl() + " . " + ojnews.getSummary());
			startActivity(Intent.createChooser(i, ojnews.getTitle()));
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK) finish();
		return super.onKeyDown(keyCode, event);
	}

	
}
