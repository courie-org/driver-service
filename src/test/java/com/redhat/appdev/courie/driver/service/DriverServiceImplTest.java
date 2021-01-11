package com.redhat.appdev.courie.driver.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.redhat.appdev.courie.driver.data.DriverRepository;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DriverServiceImplTest {

	private DriverRepository repo = Mockito.mock(DriverRepository.class);	
	private DriverService driverService = new DriverServiceImpl(null, null, null, null, null, null, repo);
	
	@Test
	public void shouldUpdateDriver() {
		Driver driver = new Driver("12", "Ron Weasley", "cx-wefsd", "Magical Car", new LatLng("10", "10"));
		driverService.update(driver);
		
		Mockito.verify(repo, Mockito.atLeast(1)).save(driver);
		
	}
}
