package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.entity.Music;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseMusic {
	
	public List<Music> parse(String data) throws JSONException {
		List<Music> list = new ArrayList<Music>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			if (item.length() > 0) {
				for (int i = 0; i < item.length(); i++) {
					JSONObject jsonItem = item.getJSONObject(i);
					Music o = new Music();
					o.setTitle(jsonItem.getString("title"));
					o.setDetail(jsonItem.getString("detail"));
					o.setUrl(jsonItem.getString("url"));
					list.add(o);
				}
			}
		}
		return list;
	}

}
