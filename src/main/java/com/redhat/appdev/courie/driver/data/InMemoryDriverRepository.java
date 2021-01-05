package com.redhat.appdev.courie.driver.data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.redhat.appdev.courie.driver.domain.Driver;

@InMemory
public class InMemoryDriverRepository implements DriverRepository {

	private Set<Driver> driverStore = new HashSet<>();
	
	@Override
	public Optional<Driver> findById(String id) {
		return this.driverStore.stream()
				.filter(d -> d.getId().equals(id))
				.findFirst();
	}

	@Override
	public void add(Driver newDelivery) {
		this.driverStore.add(newDelivery);
		
	}

	@Override
	public void save(Driver d) {
		this.driverStore.remove(d);
		this.driverStore.add(d);
		
	}

	@Override
	public Optional<Driver> findAvailableDriver() {
		return this.driverStore.stream()
				.filter(d -> d.isAvailable())
				.findFirst();
	}

	@Override
	public Set<Driver> getAllDrivers() {
		return this.driverStore;
	}

}
