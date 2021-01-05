package com.redhat.appdev.courie.driver.service;

import java.util.Optional;
import java.util.Set;

import com.redhat.appdev.courie.driver.domain.Driver;

public interface DriverService {

	public void acceptDelivery(String driverId, String deliveryId);

	public void start(String driverId, String deliveryId);

	public void pickup(String driverId, String deliveryId);

	public void dropoff(String driverId, String deliveryId);

	public Set<Driver> getAllDrivers();

	public Optional<Driver> getDriverBy(String driverId);

	public void newDriver(Driver driver);

	public void update(Driver driver);

}