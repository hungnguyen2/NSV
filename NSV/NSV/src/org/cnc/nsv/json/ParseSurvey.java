package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.entity.Survey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseSurvey {
	
	String TAG = ParseSurvey.class.getSimpleName();

	public List<Survey> parse(String data) throws JSONException {
		List<Survey> list = new ArrayList<Survey>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			if (item.length() > 0) {
				for (int i = 0; i < item.length(); i++) {
					JSONObject jsonItem = item.getJSONObject(i);
					Survey o = new Survey();
					JSONObject s = jsonItem.optJSONObject("survey");
					if (s != null) {
						o.setTitle(s.getString("title"));
						o.setUrl(s.getString("url"));
					}
					list.add(o);
				}
			}
		}
		return list;
	}

}
