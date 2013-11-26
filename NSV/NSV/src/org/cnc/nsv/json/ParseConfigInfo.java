package org.cnc.nsv.json;

import org.cnc.nsv.entity.ConfigInfo;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseConfigInfo {
	

	public ConfigInfo parse(String data) throws JSONException {
		ConfigInfo info = new ConfigInfo();
		if (data != null) {
			JSONObject root = new JSONObject(data);
			JSONObject item = root.getJSONObject("items");
			info.setNewsFeedUrl(item.getString("news_feed_url"));
			info.setGameFeedUrl(item.getString("game_feed_url"));
		}
		return info;
	}

}
