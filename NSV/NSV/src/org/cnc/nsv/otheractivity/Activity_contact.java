package org.cnc.nsv.otheractivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.cnc.nsv.Activity_main;
import org.cnc.nsv.Constant;
import org.cnc.nsv.R;
import org.cnc.nsv.createmenu;
import org.cnc.nsv.webservice.Rest;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_contact extends Activity {

	EditText etName, etEmail, etPhone, etSubject, etMessage;
	String name, email, phone, subject, message;
	Button btSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact);
		new createmenu().create(this);
		
		etName = (EditText) findViewById(R.id.etName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPhone = (EditText) findViewById(R.id.etPhone);
		etSubject = (EditText) findViewById(R.id.etSubject);
		etMessage = (EditText) findViewById(R.id.etMessage);
		btSend = (Button) findViewById(R.id.btSend);
		
		btSend.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isValid = true;
				name = etName.getText().toString();
				if (name.isEmpty()) {
					etName.setError(getBaseContext().getString(R.string.field_required));
					isValid = false;
				}
				email = etEmail.getText().toString();
				if (email.isEmpty()) {
					etEmail.setError(getBaseContext().getString(R.string.field_required));
					isValid = false;
				}
				phone = etPhone.getText().toString();
				subject = etSubject.getText().toString();
				if (subject.isEmpty()) {
					etSubject.setError(getBaseContext().getString(R.string.field_required));
					isValid = false;
				}
				message = etMessage.getText().toString();
				if (message.isEmpty()) {
					etMessage.setError(getBaseContext().getString(R.string.field_required));
					isValid = false;
				}
				if (isValid) {
					new SendMessageTask().execute();
				}
			}
		});
	}
	
	class SendMessageTask extends AsyncTask<Void, Void, String> {
		
		ProgressDialog pd;
		
		@Override
		protected void onPreExecute() {
			pd = new ProgressDialog(Activity_contact.this);
			pd.setMessage(getString(R.string.sending));
			pd.show();
			super.onPreExecute();
		}
		
		@Override
		protected String doInBackground(Void... p) {
			List<NameValuePair>  params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
			params.add(new BasicNameValuePair("email", email));
			params.add(new BasicNameValuePair("phone", phone));
			params.add(new BasicNameValuePair("subject", subject));
			params.add(new BasicNameValuePair("message", message));
			return Rest.post(Constant.API_CONTACT, params);
		}
		
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			pd.dismiss();
			if (result != null) {
				try {
					JSONObject json = new JSONObject(result);
					String msg = json.getString("message");
					Toast.makeText(Activity_contact.this, msg, Toast.LENGTH_LONG).show();
					etName.setText("");
					etPhone.setText("");
					etEmail.setText("");
					etSubject.setText("");
					etMessage.setText("");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

	@Override
	public void onBackPressed() {
		Intent i = new Intent(this, Activity_main.class);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}
}
