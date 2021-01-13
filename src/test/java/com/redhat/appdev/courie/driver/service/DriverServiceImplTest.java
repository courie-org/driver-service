package com.redhat.appdev.courie.driver.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Test;

import com.redhat.appdev.courie.driver.data.DriverRepository;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;
import com.redhat.appdev.courie.driver.messaging.DriverAssigned;
import com.redhat.appdev.courie.driver.utlis.Utils;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class DriverServiceImplTest {

	private DriverRepository repo = mock(DriverRepository.class);
	
	private DeliveryApiClient apiClient = mock(DeliveryApiClient.class);
	
	
	@SuppressWarnings("unchecked")
	private Emitter<DriverAssigned> driverAssignedEmitter = mock(Emitter.class);
	
	private DriverService driverService = new DriverServiceImpl(apiClient, 
			driverAssignedEmitter, null, null, null, null, repo);
	
	@Test
	public void shouldUpdateDriver() {
		Driver driver = Utils.RON;
		driverService.update(driver);
		
		verify(repo, times(1)).save(driver);
		
	}
	
	@Test
	public void shouldAddNewDriver() {
		Driver driver = Utils.RON;
		driverService.newDriver(driver);
		
		verify(repo, times(1)).save(driver);
		
	}
	
	@Test
	public void shouldGetDriverById() {
		Driver driver = Utils.RON;
		
		when(this.repo.findById(driver.getId())).thenReturn(Optional.of(driver));
		
		Optional<Driver> fetchedDriver = driverService.getDriverBy(driver.getId());
		
		verify(repo, times(1)).findById(driver.getId());
		
		assertEquals(driver, fetchedDriver.get());
		
	}
	
	@Test
	public void shouldGetAllDrivers() {
		driverService.getAllDrivers();
		verify(repo, times(1)).getAllDrivers();
	}
	
	@Test
	public void shouldAcceptDelivery() throws InterruptedException {
		Driver driver = Utils.RON;
		
		DeliveryResponse delivery = new DeliveryResponse(
				new LatLng("251", "251"), "123 Pickup Dr.", 
				new LatLng("252", "252"), "567 Dropoff Ln."
		);
		
		CompletionStage<DeliveryResponse> apiResult = 
				CompletableFuture.completedFuture(delivery);
		
		when(this.repo.findById(driver.getId())).thenReturn(Optional.of(driver));
		when(this.apiClient.getDelivery("1")).thenReturn(apiResult);
		
		driverService.acceptDelivery(driver.getId(), "1");
		
		// TODO: Nasty. Need to find a better way. 
		Thread.sleep(1000);
		
		verify(repo, times(1)).save(driver);
		verify(driverAssignedEmitter, times(1)).send(any(DriverAssigned.class));
		
		assertEquals(1, driver.getAssignments().size());
		assertTrue(driver.isAvailable());
		
	}
}
