package com.redhat.appdev.courie.driver.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.redhat.appdev.courie.driver.data.DriverRepository;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.utlis.Utils;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DriverServiceImplTest {

	private DriverRepository repo = Mockito.mock(DriverRepository.class);	
	private DriverService driverService = new DriverServiceImpl(null, null, null, null, null, null, repo);
	
	@Test
	public void shouldUpdateDriver() {
		Driver driver = Utils.RON;
		driverService.update(driver);
		
		Mockito.verify(repo, Mockito.atLeast(1)).save(driver);
		
	}
}
