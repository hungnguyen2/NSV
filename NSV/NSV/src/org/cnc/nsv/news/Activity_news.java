package org.cnc.nsv.news;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.store;
import org.cnc.nsv.json.ParseNews;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_news extends Activity implements Constant {
	String TAG = Activity_news.class.getSimpleName();
	ListView lvNews;
	NewsAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		new createmenu().create(Activity_news.this);
		
		findViewById(R.id.menu_news).setSelected(true);
		
		lvNews = (ListView) findViewById(R.id.lvNews);
		lvNews.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {

				NewsAdapter adapter = (NewsAdapter) parent.getAdapter();

				store.OJNEWS = (Ojnews) adapter.getItem(position);
				Intent intent = new Intent(getBaseContext(),
						Activity_detailnews.class);
				startActivity(intent);

			}
		});
		
		String json = Utils.getCatcheText(getBaseContext(), KEY_NEWS_LIST);
		if (json != null) {
			setListView(json);
			lvNews.setAdapter(adapter);
		} else {
			new resultsNews().execute();
		}

	}

	void setListView(String json) {
		List<Ojnews> listNews = new ArrayList<Ojnews>();
		try {
			listNews = new ParseNews().parse(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new NewsAdapter(getBaseContext(), listNews);
	}
	
	class resultsNews extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(Activity_news.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
			lvNews.setAdapter(null);
			super.onPreExecute();
		}

		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String json = getData.get(API_NEWS);
			Utils.setCatcheText(getApplicationContext(), KEY_NEWS_LIST, json);
			setListView(json);
			return null;
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Void result) {
			if (adapter.getCount() == 0) {
				Toast.makeText(getBaseContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
			}
			lvNews.setAdapter(adapter);
			dialog.dismiss();
			super.onPostExecute(result);
		}

	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, Activity_main.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

}
