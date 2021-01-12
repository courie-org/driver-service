package com.redhat.appdev.courie.driver.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.utlis.Utils;

import io.quarkus.test.Mock;

@Mock
@ApplicationScoped
public class MockDriverService implements DriverService {

	@Override
	public void acceptDelivery(String driverId, String deliveryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start(String driverId, String deliveryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pickup(String driverId, String deliveryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dropoff(String driverId, String deliveryId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Driver> getAllDrivers() {
		Set<Driver> drivers = new HashSet<Driver>();
		
		drivers.add(Utils.RON);
		
		return drivers;
	}

	@Override
	public Optional<Driver> getDriverBy(String driverId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void newDriver(Driver driver) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Driver driver) {
		// TODO Auto-generated method stub
		
	}

}
