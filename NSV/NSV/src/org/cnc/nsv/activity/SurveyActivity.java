package org.cnc.nsv.activity;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.adapter.SurveyAdapter;
import org.cnc.nsv.entity.Survey;
import org.cnc.nsv.json.ParseSurvey;
import org.cnc.nsv.otheractivity.Activity_info;
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

public class SurveyActivity extends Activity implements Constant {

	ListView lvResult;
	SurveyAdapter adapter;
	ImageView ivBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_survey);

		new createmenu().create(this);
		findViewById(R.id.menu_more).setSelected(true);
		
		lvResult = (ListView) findViewById(R.id.lvResult);
		ivBack = (ImageView) findViewById(R.id.button_back);
		
		lvResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Survey a = adapter.getItem(position);
				Intent i = new Intent(SurveyActivity.this, Activity_info.class);
				i.putExtra(Constant.OPEN_ACTIVITY, "survey");
				i.putExtra(KEY_SURVEY_LIST, a);
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
		
		String json = Utils.getCatcheText(getBaseContext(), KEY_SURVEY_LIST);
		if (json != null) {
			setListView(json);
			lvResult.setAdapter(adapter);
		} else {
			new LoadSurveyTask().execute();
		}
	}

	void setListView(String json) {
		List<Survey> list = new ArrayList<Survey>();
		try {
			list = new ParseSurvey().parse(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		adapter = new SurveyAdapter(this, list);
	}
	
	class LoadSurveyTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(SurveyActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
			lvResult.setAdapter(null);
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String json = getData.get(API_AGENT);
			Utils.setCatcheText(getApplicationContext(), KEY_SURVEY_LIST, json);
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
