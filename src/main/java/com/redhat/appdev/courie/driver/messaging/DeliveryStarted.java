package com.redhat.appdev.courie.driver.messaging;

public class DeliveryStarted {

	private String deliveryId;
	
	public DeliveryStarted(String deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getDeliveryId() {
		return deliveryId;
	}
	
	
}
