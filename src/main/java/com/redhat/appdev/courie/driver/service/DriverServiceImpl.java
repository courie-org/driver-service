package com.redhat.appdev.courie.driver.service;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.redhat.appdev.courie.driver.data.DriverRepository;
import com.redhat.appdev.courie.driver.domain.DeliveryAssignment;
import com.redhat.appdev.courie.driver.domain.DeliveryDirections;
import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.messaging.DeliveryDroppedOff;
import com.redhat.appdev.courie.driver.messaging.DeliveryPickedUp;
import com.redhat.appdev.courie.driver.messaging.DeliveryStarted;
import com.redhat.appdev.courie.driver.messaging.DriverAssigned;

@ApplicationScoped
public class DriverServiceImpl implements DriverService {
	
	private DeliveryApiClient client;
	private Emitter<DriverAssigned> driverAssignedEmitter;
	private Emitter<DeliveryStarted> deliveryStartedEmitter;
	private Emitter<DeliveryPickedUp> deliveryPickedUpEmitter;
	private Emitter<DeliveryDroppedOff> deliveryDroppedOffEmitter;
	private DirectionsService directions;
	private DriverRepository repository;
	
	@Inject
	public DriverServiceImpl(@RestClient DeliveryApiClient client, 
			@Channel("driver-assigned") Emitter<DriverAssigned> driverAssignedEmitter,
			@Channel("delivery-started") Emitter<DeliveryStarted> deliveryStartedEmitter, 
			@Channel("delivery-pickedup") Emitter<DeliveryPickedUp> deliveryPickedUpEmitter,
			@Channel("delivery-droppedoff") Emitter<DeliveryDroppedOff> deliveryDroppedOffEmitter,
			DirectionsService directions, 
			DriverRepository repository) {
		this.client = client;
		this.driverAssignedEmitter = driverAssignedEmitter;
		this.deliveryStartedEmitter = deliveryStartedEmitter;
		this.deliveryPickedUpEmitter = deliveryPickedUpEmitter;
		this.deliveryDroppedOffEmitter = deliveryDroppedOffEmitter;
		this.directions = directions;
		this.repository = repository;
		
	}

	@Override
	public void acceptDelivery(String driverId, String deliveryId) {
		
		Optional<Driver> d = this.repository.findById(driverId);
		
		if (d.isPresent()) {
			Driver driver = d.get();
			
			client.getDelivery(deliveryId).whenCompleteAsync((delivery, err) -> {
				
				if (delivery != null) {
					DeliveryAssignment deliveryAssignment = 
							new DeliveryAssignment(deliveryId, delivery.getPickupLatLng(), 
									delivery.getPickupAddress(), delivery.getDropoffLatLng(), 
									delivery.getDropoffAddress(), new Date());
					
					driver.assignDelivery(deliveryAssignment);
					
					this.repository.save(driver);
					
					this.driverAssignedEmitter.send(new DriverAssigned(driver.getId(), deliveryId, 
							delivery.getPickupLatLng(), delivery.getPickupAddress(), 
							delivery.getDropoffLatLng(), delivery.getDropoffAddress()));
				}
				
			});
		}
	}

	@Override
	public void start(String driverId, String deliveryId) {
		
		Optional<Driver> d = this.repository.findById(driverId);
		
		if (d.isPresent()) {
			Driver driver = d.get();
			
			driver.startDelivery(deliveryId);
			DeliveryAssignment delivery = driver.getCurrentDelivery();
			
			if (delivery != null) {
				
				DeliveryDirections calculatedDirections = this.directions.calculateDirections(driver.getCurrentLatLng(), 
						delivery.getPickupLatLng(), delivery.getDropoffLatLng());
				
				delivery.setDirections(calculatedDirections);
				
			}
			
			this.repository.save(driver);
			deliveryStartedEmitter.send(new DeliveryStarted(deliveryId));
				
		}
	}

	@Override
	public void pickup(String driverId, String deliveryId) {
		
		Optional<Driver> d = this.repository.findById(driverId);
		
		if (d.isPresent()) {
			Driver driver = d.get();
			driver.pickupDelivery();
			
			this.repository.save(driver);
			this.deliveryPickedUpEmitter.send(new DeliveryPickedUp(deliveryId));
		}
		
	}

	@Override
	public void dropoff(String driverId, String deliveryId) {
		
		Optional<Driver> d = this.repository.findById(driverId);
		
		if (d.isPresent()) {
			Driver driver = d.get();
			driver.dropoff();
			
			this.repository.save(driver);
			this.deliveryDroppedOffEmitter.send(new DeliveryDroppedOff(deliveryId));
		}
		
	}

	@Override
	public Set<Driver> getAllDrivers() {
		return this.repository.getAllDrivers();
	}

	@Override
	public Optional<Driver> getDriverBy(String driverId) {
		return this.repository.findById(driverId);
	}

	@Override
	public void newDriver(Driver driver) {
		this.repository.add(driver);
	}

	@Override
	public void update(Driver driver) {
		this.repository.save(driver);
	}

}
