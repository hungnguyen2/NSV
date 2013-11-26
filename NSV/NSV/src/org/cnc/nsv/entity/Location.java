package org.cnc.nsv.entity;

import java.io.Serializable;

public class Location implements Serializable {

	private static final long serialVersionUID = -292064534534398L;
	int id;
	String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
