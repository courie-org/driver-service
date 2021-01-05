package com.redhat.appdev.courie.driver.location;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import com.redhat.appdev.courie.driver.domain.DeliveryAssignment;
import com.redhat.appdev.courie.driver.domain.DeliveryDirections;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;
import com.redhat.appdev.courie.driver.service.DriverService;

import io.reactivex.Flowable;

@ApplicationScoped
public class LocationGenerator {
	
	private static final Logger LOG = Logger.getLogger(LocationGenerator.class);
	
	private DriverService driverService;
	private DriverLocationEventSocket socket;
	
	@Inject
	public LocationGenerator(DriverService driverService, 
			DriverLocationEventSocket socket) {
		
		this.driverService = driverService;
		this.socket = socket;
	}
	
	@Outgoing("driver-location")
	public Flowable<List<DriverLocation>> publishLocation() {
		return Flowable.interval(2, TimeUnit.SECONDS).map(tick -> { 
			
			Set<Driver> drivers = this.driverService.getAllDrivers();
			List<DriverLocation> locUpdates = new ArrayList<>();
			
			for (Driver driver : drivers) {
				
				DriverLocation currentLocation = new DriverLocation(driver, driver.getCurrentLatLng());
				
				if (driver.hasDeliveryDirections()) {
					
					DeliveryAssignment assignment = driver.getCurrentDelivery();
					DeliveryDirections directions = assignment.getDirections();
					
					if (!directions.isFinished()) {
						
						LatLng locUpdate = directions.nextLocation();
						
						driver.updateLocation(locUpdate);
						currentLocation = new DriverLocation(driver, locUpdate);
						
						driverService.update(driver);
						
					}
				}
				
				LOG.info("Socket Broadcast for location update: " + currentLocation);
				this.socket.broadcast(currentLocation);
				locUpdates.add(currentLocation);
			}
			
			return locUpdates;
			
		});
	}
}
