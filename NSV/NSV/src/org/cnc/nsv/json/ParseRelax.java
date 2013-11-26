package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.entity.Relax;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseRelax {
	
	String TAG = ParseRelax.class.getSimpleName();

	public List<Relax> parse(String data) throws JSONException {
		List<Relax> list = new ArrayList<Relax>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			if (item.length() > 0) {
				for (int i = 0; i < item.length(); i++) {
					JSONObject jsonItem = item.getJSONObject(i);
					Relax o = new Relax();
					o.setTitle(jsonItem.getString("title"));
					o.setSummary(jsonItem.getString("detail"));
					o.setUrl(jsonItem.getString("url"));
					o.setThumbUrl(jsonItem.getString("image_thumb"));
					o.setImageUrl(jsonItem.getString("image"));
					list.add(o);
				}
			}
		}
		return list;
	}

}
