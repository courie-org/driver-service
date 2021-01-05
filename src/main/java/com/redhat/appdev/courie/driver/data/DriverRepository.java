package com.redhat.appdev.courie.driver.data;

import java.util.Optional;
import java.util.Set;

import com.redhat.appdev.courie.driver.domain.Driver;

public interface DriverRepository {
	public Optional<Driver> findById(String id);
	public Optional<Driver> findAvailableDriver();
	public void add(Driver driver);
	public Set<Driver> getAllDrivers();
	public void save(Driver driver);
}
