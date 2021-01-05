package com.redhat.appdev.courie.driver.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Driver {

	private String id;
	private String name;
	private String licenseNumber;
	private String carDescription;
	private boolean isAvailable;
	private LatLng currentLatLng;
	private List<DeliveryAssignment> deliveryHistory;
	private Set<DeliveryAssignment> assignments;
	private DeliveryAssignment currentDelivery;
	
	public Driver(String id, String name, String licenseNumber, String carDescription, LatLng currentLatLng) {
		this.id = id;
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.carDescription = carDescription;
		this.currentLatLng = currentLatLng;
		this.isAvailable = true;
		this.deliveryHistory = new ArrayList<>();
		this.assignments = new HashSet<DeliveryAssignment>();
		this.currentDelivery = null;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public String getCarDescription() {
		return carDescription;
	}

	public LatLng getCurrentLatLng() {
		return currentLatLng;
	}

	public List<DeliveryAssignment> getDeliveryHistory() {
		return deliveryHistory;
	}

	public Set<DeliveryAssignment> getAssignments() {
		return assignments;
	}
	
	public DeliveryAssignment getCurrentDelivery() {
		return currentDelivery;
	}
	
	public boolean isAvailable() {
		return isAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((licenseNumber == null) ? 0 : licenseNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Driver other = (Driver) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (licenseNumber == null) {
			if (other.licenseNumber != null)
				return false;
		} else if (!licenseNumber.equals(other.licenseNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", name=" + name + ", licenseNumber=" + licenseNumber + ", carDescription="
				+ carDescription + ", isAvailable=" + isAvailable + ", currentLatLng=" + currentLatLng + "]";
	}
	
	public void assignDelivery(DeliveryAssignment deliveryAssignment) {
		this.assignments.add(deliveryAssignment);
	}

	public void updateLocation(LatLng positionUpdate) {
		this.currentLatLng = positionUpdate;
		
	}

	public void startDelivery(String deliveryId) {
		
		if (this.isAvailable && !this.assignments.isEmpty()) {
			this.isAvailable = false;
			
			Optional<DeliveryAssignment> deliveryMaybe = this.assignments.stream()
				.filter((assignment) -> assignment.getId().equals(deliveryId))
				.findFirst();
			
			if (deliveryMaybe.isPresent()) {
				
				DeliveryAssignment deliveryToStart = deliveryMaybe.get();
				this.assignments.remove(deliveryToStart);
				this.currentDelivery = deliveryToStart;
			}
			
		}
		
	}

	public boolean hasDeliveryDirections() {
		return !this.isAvailable && this.currentDelivery != null 
				&& this.currentDelivery.getDirections() != null;
	}

	public void pickupDelivery() {
		if (this.currentDelivery != null) {
			this.currentDelivery.setHasPackage(true);
		}
	}

	public void dropoff() {

		if (this.currentDelivery != null) {
			this.currentDelivery.setHasPackage(false);
			this.deliveryHistory.add(this.currentDelivery);
			this.currentDelivery = null;
			this.isAvailable = true;
		}
		
	}
		
}
