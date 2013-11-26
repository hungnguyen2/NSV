package org.cnc.nsv.entity;

import java.io.Serializable;

public class ConfigInfo implements Serializable {

	private static final long serialVersionUID = -2343434159896391398L;
	int id;
	String newsFeedUrl, gameFeedUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNewsFeedUrl() {
		return newsFeedUrl;
	}

	public void setNewsFeedUrl(String newsFeedUrl) {
		this.newsFeedUrl = newsFeedUrl;
	}

	public String getGameFeedUrl() {
		return gameFeedUrl;
	}

	public void setGameFeedUrl(String gameFeedUrl) {
		this.gameFeedUrl = gameFeedUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
