package com.redhat.appdev.courie.driver.domain;

public class LatLng {

	private String lat;
	private String lng;
	
	public LatLng(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}

	public String getLat() {
		return lat;
	}

	public String getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return "LatLng [lat=" + lat + ", lng=" + lng + "]";
	}
	
}
