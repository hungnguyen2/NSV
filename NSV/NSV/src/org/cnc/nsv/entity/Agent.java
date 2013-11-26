package org.cnc.nsv.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class Agent implements Serializable {

	private static final long serialVersionUID = 4887771775005519932L;
	int id;
	String name, address, phone, email, website, thumbUrl, imageUrl, summary, type;
	float geoLat, geoLng;
	Bitmap thumb;
	
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
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getSummary() {
		return summary;
	}
	
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	public float getGeoLat() {
		return geoLat;
	}
	
	public void setGeoLat(float geoLat) {
		this.geoLat = geoLat;
	}
	public float getGeoLng() {
		return geoLng;
	}
	
	public void setGeoLng(float geoLng) {
		this.geoLng = geoLng;
	}
	
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Bitmap getThumb() {
		return thumb;
	}

	public void setThumb(Bitmap thumb) {
		this.thumb = thumb;
	}

}
