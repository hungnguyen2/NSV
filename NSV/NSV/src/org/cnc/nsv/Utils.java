package org.cnc.nsv;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.cnc.nsv.entity.ConfigInfo;
import org.cnc.nsv.entity.Geo;
import org.cnc.nsv.json.ParseConfigInfo;
import org.cnc.nsv.webservice.GetData;
import org.json.JSONException;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

public class Utils implements Constant {

	public static Geo geo;
	public static ConfigInfo configInfo;
	
	public static Bitmap getImageFromUrl(String str) {
		try {
			URL url = new URL(str);
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			Bitmap bm = BitmapFactory.decodeStream(is);
			is.close();
			return bm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static class LoadImageTask2 extends AsyncTask<String, Void, Bitmap> {

		ImageView iv;

		public LoadImageTask2(ImageView iv) {
			this.iv = iv;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bm = Utils.getImageFromUrl(params[0]);
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null)
				iv.setImageBitmap(result);
		}
	}

	public static class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

		Handler h;

		public LoadImageTask(Handler h) {
			this.h = h;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bm = Utils.getImageFromUrl(params[0]);
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				Message msg = new Message();
				msg.what = 1;
				msg.obj = result;
				h.sendMessage(msg);
			}
		}
	}
	
	public static String getCatcheText(Context c, String tag) {
		String str = null;
		try {
			SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
			str = p.getString(tag, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static void setCatcheText(Context c, String key, String value) {
		if (value != null) {
			try {
				SharedPreferences p = PreferenceManager
						.getDefaultSharedPreferences(c);
				p.edit().putString(key, value).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void clearCatcheText(Context c) {
		try {
			SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(c);
			p.edit().clear().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void preLoadData(Context context) {
		clearCatcheText(context);
		geo = getCurrentLocation(context);
		//String geoParams = (geo == null) ? "" : "?lat=" + geo.getLat() + "&lng=" + geo.getLng();
		new LoadDataTask(context, KEY_CONFIG, API_CONFIG).execute();
		new LoadDataTask(context, KEY_PRODUCT_LIST, API_PRODUCT).execute();
		new LoadDataTask(context, KEY_LOCATION_LIST, API_LOCATION).execute();
		new LoadDataTask(context, KEY_NEWS_LIST, API_NEWS).execute();
		//new LoadDataTask(context, KEY_AGENT_LIST, API_AGENT + geoParams).execute();
		//new LoadDataTask(context, KEY_CONTRACTOR_LIST, API_CONTRACTOR + geoParams).execute();
		new LoadDataTask(context, KEY_SURVEY_LIST, API_SURVEY).execute();
		//new LoadDataTask(context, KEY_RELAX_LIST, API_RELAX).execute();
		new LoadDataTask(context, KEY_MUSIC_LIST, API_MUSIC).execute();
	}
	
	public static class LoadDataTask extends AsyncTask<Void, Void, Void> {

		Context context;
		String cacheKey, url, result;

		public LoadDataTask(Context context, String cacheKey, String url) {
			this.context = context;
			this.cacheKey = cacheKey;
			this.url = url;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			if (geo == null) geo = getCurrentLocation(context);
		}

		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			result = getData.get(url);
			return null;
		}

		@Override
		protected void onPostExecute(Void r) {
			super.onPostExecute(r);
			Utils.setCatcheText(context, cacheKey, result);
			Log.d("LoadDataTask", "Load \"" + cacheKey + "\" done");
		}

	}
	
	public static Geo getCurrentLocation(Context c) {
		Geo g = null;
		Boolean gpsEnabled = false;
		Boolean networkEnabled = false;
		LocationManager lm = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);
		try {
			gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (gpsEnabled || networkEnabled) {
			try {
				Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				if (location == null) {
					location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				}
				if (location != null) {
					g = new Geo();
					g.setLat(location.getLatitude());
					g.setLng(location.getLongitude());
					Log.d("Geo detect", "lat: " + g.getLat() + ", lng: " + g.getLng());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return g;
	}

	public static class LoadConfigInfoTask extends AsyncTask<Void, Void, Void> {
		
		ProgressDialog dialog;
		Context context;
		
		public LoadConfigInfoTask(Context context) {
			this.context = context;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(context);
			dialog.setMessage(context.getString(R.string.loading));
			dialog.setCancelable(false);
			dialog.show();
		}
		@Override
		protected Void doInBackground(Void... params) {
			GetData getData = new GetData();
			String json = getData.get(API_CONFIG);
			if (json != null) {
				try {
					configInfo = new ParseConfigInfo().parse(json);
					setCatcheText(context, KEY_CONFIG, json);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			dialog.dismiss();
		}
	}
	
	public static void getConfigInfo(Context context) {
		configInfo = new ConfigInfo();
		String json = Utils.getCatcheText(context, KEY_CONFIG);
		if (json != null) {
			try {
				configInfo = new ParseConfigInfo().parse(json);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			new Utils.LoadConfigInfoTask(context).execute();
		}
	}
}
