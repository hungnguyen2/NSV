package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.news.Ojnews;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseNews {
	
	String TAG = ParseNews.class.getSimpleName();

	public List<Ojnews> parse(String data) throws JSONException {
		List<Ojnews> listNews = new ArrayList<Ojnews>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			for (int i = 0; i < item.length(); i++) {
				JSONObject news = item.getJSONObject(i);
				Ojnews ojnews = new Ojnews();
				ojnews.setId(news.getString("id"));
				ojnews.setTitle(news.getString("title"));
				ojnews.setUrl(news.getString("url"));
				ojnews.setThumbUrl(news.getString("image_thumb"));
				ojnews.setImageUrl(news.getString("image"));
				ojnews.setSummary(news.getString("summary"));
				ojnews.setDetail(news.getString("detail"));
				listNews.add(ojnews);
			}
		}
		return listNews;
	}
}
