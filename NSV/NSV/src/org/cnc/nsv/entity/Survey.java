package org.cnc.nsv.entity;

import java.io.Serializable;

public class Survey implements Serializable {

	private static final long serialVersionUID = -2920662159896391398L;
	int id;
	String title, url;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
