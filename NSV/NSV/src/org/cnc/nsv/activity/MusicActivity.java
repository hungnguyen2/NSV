package org.cnc.nsv.activity;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.adapter.MusicAdapter;
import org.cnc.nsv.entity.Music;
import org.cnc.nsv.json.ParseMusic;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

public class MusicActivity extends Activity implements Constant {

	ListView lvResult;
	MusicAdapter adapter;
	ImageView ivBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_music);

		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);
		
		lvResult = (ListView) findViewById(R.id.lvResult);
		ivBack = (ImageView) findViewById(R.id.button_back);
		
		lvResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Music a = adapter.getItem(position);
				Intent i = new Intent(MusicActivity.this, PlayActivity.class);
				i.putExtra(KEY_MUSIC_LIST, a);
				i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(i);
			}
		});
		
		ivBack.setVisibility(View.VISIBLE);
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		String json = Utils.getCatcheText(getBaseContext(), KEY_MUSIC_LIST);
		if (json != null) {
			setListView(json);
			lvResult.setAdapter(adapter);
		} else {
			new LoadMusicTask().execute();
		}
		
	}

	void setListView(String json) {
		List<Music> list = new ArrayList<Music>();
		try {
			list = new ParseMusic().parse(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new MusicAdapter(this, list);
	}
	
	class LoadMusicTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MusicActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
			lvResult.setAdapter(null);
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String json = getData.get(API_MUSIC);
			Utils.setCatcheText(getApplicationContext(), KEY_MUSIC_LIST, json);
			setListView(json);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (adapter.getCount() == 0) {
				Toast.makeText(getBaseContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
			}
			lvResult.setAdapter(adapter);
			dialog.dismiss();
		}
	}

}
