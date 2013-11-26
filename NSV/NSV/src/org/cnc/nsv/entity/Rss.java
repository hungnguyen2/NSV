package org.cnc.nsv.entity;

import java.io.Serializable;

public class Rss implements Serializable {

	private static final long serialVersionUID = 766634214904534519L;
	int id;
	String title, url, summary, date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
