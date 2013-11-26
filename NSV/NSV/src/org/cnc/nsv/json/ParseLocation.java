package org.cnc.nsv.json;

import java.util.ArrayList;
import java.util.List;

import org.cnc.nsv.entity.Location;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseLocation {
	
	public List<Location> parse(String data) throws JSONException {
		List<Location> list = new ArrayList<Location>();
		if (data != null) {
			JSONArray items = new JSONArray(data);
			if (items.length() > 0) {
				for (int i = 0; i < items.length(); i++) {
					JSONObject jsonItem = items.getJSONObject(i);
					Location o = new Location();
					o.setId(Integer.valueOf(jsonItem.getString("tid")));
					o.setName(jsonItem.getString("name"));
					list.add(o);
				}
			}
		}
		return list;
	}

}
