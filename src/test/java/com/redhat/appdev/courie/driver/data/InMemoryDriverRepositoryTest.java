package com.redhat.appdev.courie.driver.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.utlis.Utils;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class InMemoryDriverRepositoryTest {

	private DriverRepository driverRepo = new InMemoryDriverRepository();
	
	@Test
	public void shouldAddDriverAndFindDriver() {
		this.driverRepo.save(Utils.RON);
		Optional<Driver> shouldBeRon = this.driverRepo.findById(Utils.RON.getId());
		
		assertTrue(shouldBeRon.isPresent());
		assertTrue(shouldBeRon.get().equals(Utils.RON));
	}
	
	@Test
	public void shouldNotFindDriver() {
		this.driverRepo.save(Utils.RON);
		Optional<Driver> shouldBeRon = this.driverRepo.findById("24");
		
		assertFalse(shouldBeRon.isPresent());
	}
	
	@Test
	public void shouldSaveDriverAndFindDriver() {
		this.driverRepo.save(Utils.RON);
		Optional<Driver> shouldBeRon = this.driverRepo.findById(Utils.RON.getId());
		
		assertTrue(shouldBeRon.isPresent());
		assertTrue(shouldBeRon.get().equals(Utils.RON));
	}
	
	@Test
	public void shouldNotFindDriverWhenSaved() {
		this.driverRepo.save(Utils.RON);
		Optional<Driver> shouldBeRon = this.driverRepo.findById("24");
		
		assertFalse(shouldBeRon.isPresent());
	}
	
	@Test
	public void shouldFindAvailableDriver() {
		this.driverRepo.save(Utils.RON);
		this.driverRepo.save(Utils.UNAVALABLE);
		
		Optional<Driver> shouldBeRon = this.driverRepo.findAvailableDriver();
		
		assertTrue(shouldBeRon.isPresent());
		assertTrue(shouldBeRon.get().equals(Utils.RON));
	}
	
	@Test
	public void shouldFindNoAvailableDrivers() {
		this.driverRepo.save(Utils.UNAVALABLE);
		
		Optional<Driver> shouldBeEmpty = this.driverRepo.findAvailableDriver();
		
		assertFalse(shouldBeEmpty.isPresent());
	}
	
	@Test
	public void shouldFindAllDrivers() {
		this.driverRepo.save(Utils.RON);
		this.driverRepo.save(Utils.UNAVALABLE);
		
		Set<Driver> drivers = this.driverRepo.getAllDrivers();
		
		assertEquals(2, drivers.size());
		assertTrue(drivers.contains(Utils.RON));
		assertTrue(drivers.contains(Utils.UNAVALABLE));
	}
}
