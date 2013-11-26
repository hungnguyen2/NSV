package org.cnc.nsv.webservice;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.cnc.nsv.Constant;

import android.util.Log;

public class Rest {

	public static String post(String url, List<NameValuePair> params) {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, Constant.CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, Constant.SOCKET_TIMEOUT);
		HttpClient client = new DefaultHttpClient(httpParams);
		HttpPost request = null;
		HttpResponse response;
		request = new HttpPost(Constant.API_URL + url);
		String responseString = null;
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		try {
			response = client.execute((HttpUriRequest) request);
			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					responseString = EntityUtils.toString(entity);
					Log.d("responseString", responseString);
				}
			} else {
				responseString = response.getStatusLine().getReasonPhrase();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseString;
	}
	
}
