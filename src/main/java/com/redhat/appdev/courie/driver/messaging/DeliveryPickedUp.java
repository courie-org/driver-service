package com.redhat.appdev.courie.driver.messaging;

import java.util.Date;

public class DeliveryPickedUp {
	
	private String deliveryId;
	private Date pickupDate;
	
	public DeliveryPickedUp(String deliveryId) {
		this.deliveryId = deliveryId;
		this.pickupDate = new Date();
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

}
