package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.entity.Agent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseAgent {
	
	String TAG = ParseAgent.class.getSimpleName();

	public List<Agent> parse(String data) throws JSONException {
		List<Agent> list = new ArrayList<Agent>();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONArray item = root.getJSONArray("items");
			if (item.length() > 0) {
				for (int i = 0; i < item.length(); i++) {
					JSONObject jsonItem = item.getJSONObject(i);
					Agent o = new Agent();
					o.setId(Integer.valueOf(jsonItem.getString("id")));
					o.setName(jsonItem.getString("name"));
					o.setAddress(jsonItem.getString("address"));
					o.setPhone(jsonItem.getString("phone"));
					o.setEmail(jsonItem.getString("email"));
					o.setWebsite(jsonItem.getString("website"));
					o.setThumbUrl(jsonItem.getString("image_thumb"));
					o.setImageUrl(jsonItem.getString("image"));
					o.setType(jsonItem.getString("agent_type"));
					o.setSummary(jsonItem.getString("detail"));
					JSONObject geo = jsonItem.optJSONObject("geo");
					if (geo != null) {
						o.setGeoLat(Float.valueOf(geo.getString("lat")));
						o.setGeoLng(Float.valueOf(geo.getString("lng")));
					}
					list.add(o);
				}
			}
		}
		return list;
	}

}
