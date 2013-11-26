package org.cnc.nsv.search;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.activity.MapActivity;
import org.cnc.nsv.adapter.AgentAdapter;
import org.cnc.nsv.adapter.ContractorAdapter;
import org.cnc.nsv.entity.Agent;
import org.cnc.nsv.entity.Contractor;
import org.cnc.nsv.json.ParseAgent;
import org.cnc.nsv.json.ParseContractor;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class Activity_mainsearch extends Activity implements Constant {

	ListView lvResult;
	AgentAdapter agentAdapter;
	ContractorAdapter contractorAdapter;
	ImageView ivBack;
	EditText etSearch;
	int searchType = 1; // 1: agent, 2: contractor
	RadioButton rbAgent, rbContractor;
	String searchText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainsearch);

		new createmenu().create(this);
		findViewById(R.id.menu_search).setSelected(true);
		
		lvResult = (ListView) findViewById(R.id.lvResult);
		ivBack = (ImageView) findViewById(R.id.button_back);
		etSearch = (EditText) findViewById(R.id.etSearch);
		rbAgent = (RadioButton) findViewById(R.id.rbAgent);
		rbContractor = (RadioButton) findViewById(R.id.rbContractor);
		
		rbAgent.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) searchType = 1;
			}
		});
		
		rbContractor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) searchType = 2;
				setRadioButtonActive();
			}
		});
		setRadioButtonActive();
		
		etSearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
		etSearch.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					searchText = etSearch.getText().toString();
					if (!searchText.isEmpty()) {
						if (searchType == 1) new SearchAgentTask().execute();
						if (searchType == 2) new SearchContractorTask().execute();
						InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);
					}
				}
				return false;
			}
		});
		
		lvResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				if (searchType == 1) {
					Agent a = agentAdapter.getItem(position);
					Intent i = new Intent(Activity_mainsearch.this, MapActivity.class);
					i.putExtra("nsv-agent", a);
					startActivity(i);
				} else {
					Contractor a = contractorAdapter.getItem(position);
					Intent i = new Intent(Activity_mainsearch.this, MapActivity.class);
					i.putExtra("nsv-contractor", a);
					startActivity(i);
				}
			}
		});
		
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			findViewById(R.id.includeMenuSearch).setVisibility(View.GONE);
		} else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
			findViewById(R.id.includeMenuSearch).setVisibility(View.VISIBLE);
		}
	}
	
	@SuppressWarnings("deprecation")
	void setRadioButtonActive() {
		if (searchType == 1) {
			rbAgent.setTextColor(Color.parseColor("#f21900"));
			rbAgent.setBackgroundResource(R.drawable.tabact);
			rbContractor.setTextColor(Color.parseColor("#000000"));
			rbContractor.setBackgroundDrawable(null);
		} else {
			rbContractor.setTextColor(Color.parseColor("#f21900"));
			rbContractor.setBackgroundResource(R.drawable.tabact);
			rbAgent.setTextColor(Color.parseColor("#000000"));
			rbAgent.setBackgroundDrawable(null);
		}
	}
	
	class SearchAgentTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Activity_mainsearch.this);
			dialog.setMessage(getString(R.string.searching_agent));
			dialog.show();
			lvResult.setAdapter(null);
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String url = API_AGENT;
			url += "?name=" + Uri.encode(searchText);
			String data = getData.get(url);
			List<Agent> list = new ArrayList<Agent>();
			try {
				list = new ParseAgent().parse(data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			agentAdapter = new AgentAdapter(Activity_mainsearch.this, list, false);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lvResult.setAdapter(agentAdapter);
			dialog.dismiss();
		}
	}
	
	class SearchContractorTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(Activity_mainsearch.this);
			dialog.setMessage(getString(R.string.searching_contractor));
			dialog.show();
			lvResult.setAdapter(null);
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String url = API_CONTRACTOR;
			url += "?name=" + Uri.encode(searchText);
			String data = getData.get(url);
			List<Contractor> list = new ArrayList<Contractor>();
			try {
				list = new ParseContractor().parse(data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			contractorAdapter = new ContractorAdapter(Activity_mainsearch.this, list);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			lvResult.setAdapter(contractorAdapter);
			dialog.dismiss();
		}
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, Activity_main.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
