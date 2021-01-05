package com.redhat.appdev.courie.driver.service;

import com.redhat.appdev.courie.driver.domain.DeliveryDirections;
import com.redhat.appdev.courie.driver.domain.LatLng;

public interface DirectionsService {

	public DeliveryDirections calculateDirections(LatLng driver, LatLng pickup, LatLng dropoff);
}
