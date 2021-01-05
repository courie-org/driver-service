package com.redhat.appdev.courie.driver.messaging;

import java.util.Date;

public class DeliveryDroppedOff {
	
	private String deliveryId;
	private Date deliveredDate;
	
	public DeliveryDroppedOff(String deliveryId) {
		this.deliveryId = deliveryId;
		this.deliveredDate = new Date();
	}

	public String getDeliveryId() {
		return deliveryId;
	}

	public Date getDeliveredDate() {
		return deliveredDate;
	}

}
