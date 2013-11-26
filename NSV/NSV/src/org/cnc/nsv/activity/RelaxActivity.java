package org.cnc.nsv.activity;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class RelaxActivity extends Activity implements Constant {

	ImageView ivBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relax);

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
		
		Utils.getConfigInfo(this);
		
		findViewById(R.id.llMusic).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RelaxActivity.this, MusicActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		
		findViewById(R.id.llRss).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RelaxActivity.this, RssActivity.class);
				i.putExtra(Constant.OPEN_ACTIVITY, KEY_RSS_NEWS);
				i.putExtra(Constant.KEY_URL, Utils.configInfo.getNewsFeedUrl());
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		
		findViewById(R.id.llGame).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(RelaxActivity.this, RssActivity.class);
				i.putExtra(Constant.OPEN_ACTIVITY, KEY_RSS_GAME);
				i.putExtra(Constant.KEY_URL, Utils.configInfo.getGameFeedUrl());
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
	}
}
