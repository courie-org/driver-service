package com.redhat.appdev.courie.driver.http;

import com.redhat.appdev.courie.driver.domain.LatLng;

public class NewDriverRequest {

	private String id;
	private String name;
	private String licenseNumber;
	private String carDescription;
	private LatLng currentLatLng;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getCarDescription() {
		return carDescription;
	}
	public void setCarDescription(String carDescription) {
		this.carDescription = carDescription;
	}
	public LatLng getCurrentLatLng() {
		return currentLatLng;
	}
	public void setCurrentLatLng(LatLng currentLatLng) {
		this.currentLatLng = currentLatLng;
	}
	
}
