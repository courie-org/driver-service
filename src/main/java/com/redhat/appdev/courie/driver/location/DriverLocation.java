package com.redhat.appdev.courie.driver.location;

import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;

public class DriverLocation {

	private Driver driver;
	private LatLng driverLatLng;
	
	public DriverLocation() { }
	
	public DriverLocation(Driver driver, LatLng driverLatLng) {
		this.driver = driver;
		this.driverLatLng = driverLatLng;
	}
	
	public Driver getDriver() {
		return driver;
	}
	
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public LatLng getDriverLatLng() {
		return driverLatLng;
	}

	@Override
	public String toString() {
		return "DriverLocation [driverId=" + driver + ", driverLatLng=" + driverLatLng + "]";
	}
	
}
