package com.redhat.appdev.courie.driver.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.redhat.appdev.courie.driver.domain.LatLng;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryResponse {

	private LatLng pickupLatLng;
	private String pickupAddress;
	private LatLng dropoffLatLng;
	private String dropoffAddress;
	
	public DeliveryResponse(LatLng pickupLatLng, String pickupAddress, LatLng dropoffLatLng, String dropoffAddress) {
		this.pickupLatLng = pickupLatLng;
		this.pickupAddress = pickupAddress;
		this.dropoffLatLng = dropoffLatLng;
		this.dropoffAddress = dropoffAddress;
	}
	public LatLng getPickupLatLng() {
		return pickupLatLng;
	}
	public String getPickupAddress() {
		return pickupAddress;
	}
	public LatLng getDropoffLatLng() {
		return dropoffLatLng;
	}
	public String getDropoffAddress() {
		return dropoffAddress;
	}
	
	@Override
	public String toString() {
		return "DeliveryResponse [pickupLatLng=" + pickupLatLng + ", pickupAddress=" + pickupAddress
				+ ", dropoffLatLng=" + dropoffLatLng + ", dropoffAddress=" + dropoffAddress + "]";
	}
	
	
}
