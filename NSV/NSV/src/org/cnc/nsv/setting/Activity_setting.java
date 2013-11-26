package org.cnc.nsv.setting;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.activity.RelaxActivity;
import org.cnc.nsv.activity.SurveyActivity;
import org.cnc.nsv.otheractivity.Activity_info;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class Activity_setting extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);
		findViewById(R.id.llInfo).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Activity_setting.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "info");
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
				
			}
		});
		findViewById(R.id.llChart).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showWebDialog("file:///android_asset/sdtc.html");
				Intent i = new Intent(Activity_setting.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "chart");
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);	
			}
		});
		findViewById(R.id.llDevHistory).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showWebDialog("file:///android_asset/history.html");
				Intent i = new Intent(Activity_setting.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "history");
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		findViewById(R.id.llPhoto).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//showWebDialog(Constant.URL_PHOTO);
				Intent i = new Intent(Activity_setting.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "photo");
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		findViewById(R.id.llVideo).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.URL_VIDEO));
				startActivity(i);*/
				Intent i = new Intent(Activity_setting.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "video");
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		findViewById(R.id.llSurvey).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Activity_setting.this, SurveyActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		findViewById(R.id.llRelax).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Activity_setting.this, RelaxActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		findViewById(R.id.llYahoo).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Intent i = new Intent(Intent.ACTION_VIEW, 
							Uri.parse("ymsgr:sendIM?" + Constant.YAHOO_ACCOUNT));
					startActivity(i);
				} catch (Exception e) {
					//Toast.makeText(Activity_setting.this, getString(R.string.install_yahoo_notice), Toast.LENGTH_SHORT).show();
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.URL_GOOGLE_PLAY_YAHOO_IM));
					startActivity(i);
				}
			}
		});
		findViewById(R.id.llFacebook).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.FACEBOOK_PAGE));
				startActivity(i);
			}
		});
		findViewById(R.id.llMoreApp).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(Constant.URL_GOOGLE_PLAY));
				startActivity(i);
			}
		});
	}
	
	void showWebDialog(String url) {
		final AlertDialog.Builder d = new AlertDialog.Builder(this);
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage(getString(R.string.loading));
		pd.show();
		d.setNegativeButton(R.string.close, null);
		final WebView w = new WebView(Activity_setting.this);
		w.getSettings().setSupportZoom(true);
		w.getSettings().setBuiltInZoomControls(true);
		w.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		w.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				if (newProgress >= 100) {
					try {
						d.setView(w);
						d.show();
						pd.dismiss();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		w.loadUrl(url);
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, Activity_main.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
