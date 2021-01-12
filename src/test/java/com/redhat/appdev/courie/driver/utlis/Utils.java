package com.redhat.appdev.courie.driver.utlis;

import java.util.Date;

import com.redhat.appdev.courie.driver.domain.DeliveryAssignment;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;

public class Utils {

	public static final Driver RON = new Driver("1", "Ron Weasley", "cx-wefsd", "Magical Car", new LatLng("10", "10"));
	public static final Driver UNAVALABLE = new Driver("2", "Mr. Busy", "Processing..", "BMW", new LatLng("250", "250"));
	
	static {
		UNAVALABLE.assignDelivery(new DeliveryAssignment("1", 
				new LatLng("251", "251"), "123 Pickup Dr.", 
				new LatLng("252", "252"), "567 Dropoff Ln.", new Date()));
		
		UNAVALABLE.startDelivery("1");
		
	}
}
