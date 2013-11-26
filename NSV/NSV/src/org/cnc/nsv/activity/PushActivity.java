package org.cnc.nsv.activity;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

public class PushActivity extends Activity implements Constant {

	ImageView ivBack;
	TextView tvContent;
	WebView wvContent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_push);

		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);
		
		ivBack = (ImageView) findViewById(R.id.button_back);
		
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		tvContent = (TextView) findViewById(R.id.tvContent);
		
		wvContent = (WebView) findViewById(R.id.wvContent);
		wvContent.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}
		});
		
		try {
			JSONObject json = new JSONObject(getIntent().getExtras().getString("com.parse.Data"));
			String message = json.getString("alert");
			//wvContent.loadData(message, "text/html;charset=utf-8", "utf-8");
			tvContent.setText(message);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
