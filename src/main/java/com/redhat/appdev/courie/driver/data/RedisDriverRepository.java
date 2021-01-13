package com.redhat.appdev.courie.driver.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.inject.Inject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.redhat.appdev.courie.driver.domain.Driver;

import io.quarkus.redis.client.RedisClient;

public class RedisDriverRepository implements DriverRepository {

	private static final String PREFIX = "driver:";
	
	private RedisClient client;
	
	@Inject
	public RedisDriverRepository(RedisClient client) {
		this.client = client;
	}
	
	@Override
	public Optional<Driver> findById(String id) {
		Gson gson = new GsonBuilder().create();
		String response = this.client.get(calculateId(id)).toString();
		
		Driver d = gson.fromJson(response, Driver.class);
		
		return Optional.ofNullable(d);
	}

	@Override
	public Optional<Driver> findAvailableDriver() {
		return getAllDrivers().stream()
			.filter(d -> d.isAvailable())
			.findFirst();
	}

	@Override
	public Set<Driver> getAllDrivers() {
		Set<Driver> drivers = new HashSet<>();
		Gson gson = new GsonBuilder().create();
		
		this.client.keys(PREFIX+"*").forEach(r -> {
			
			String value = this.client.get(r.toString()).toString();
			Driver d = gson.fromJson(value, Driver.class);
			drivers.add(d);
		});
		
		return drivers;
	}

	@Override
	public void save(Driver driver) {
		Gson gson = new GsonBuilder().create();
		this.client.set(Arrays.asList(calculateId(driver.getId()), gson.toJson(driver)));
	}
	
	private String calculateId(String driverId) {
		return PREFIX + driverId;
	}

}
