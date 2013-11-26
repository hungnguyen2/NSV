package org.cnc.nsv.activity;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.Utils;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.adapter.ContractorAdapter;
import org.cnc.nsv.entity.Contractor;
import org.cnc.nsv.json.ParseContractor;
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

public class ContractorActivity extends Activity implements Constant {

	ListView lvResult;
	ContractorAdapter contractorAdapter;
	ImageView ivBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contractor);

		new createmenu().create(this);
		
		lvResult = (ListView) findViewById(R.id.lvResult);
		ivBack = (ImageView) findViewById(R.id.button_back);
		lvResult.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				Contractor a = contractorAdapter.getItem(position);
				Intent i = new Intent(ContractorActivity.this, MapActivity.class);
				i.putExtra("nsv-contractor", a);
				startActivity(i);
			}
		});
		
		ivBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		String json = Utils.getCatcheText(getBaseContext(), KEY_CONTRACTOR_LIST);
		if (json != null) {
			setListView(json);
			lvResult.setAdapter(contractorAdapter);
		} else {
			new SearchContractorTask().execute();
		}
		
	}

	void setListView(String json) {
		List<Contractor> list = new ArrayList<Contractor>();
		try {
			list = new ParseContractor().parse(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		contractorAdapter = new ContractorAdapter(this, list);
	}
	
	class SearchContractorTask extends AsyncTask<Void, Void, Void> {
		ProgressDialog dialog;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(ContractorActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.show();
			lvResult.setAdapter(null);
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String json = getData.get(API_CONTRACTOR);
			Utils.setCatcheText(getApplicationContext(), KEY_CONTRACTOR_LIST, json);
			setListView(json);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			if (contractorAdapter.getCount() == 0) {
				Toast.makeText(getBaseContext(), getString(R.string.check_internet), Toast.LENGTH_LONG).show();
			}
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
