package com.redhat.appdev.courie.driver.domain;

import java.util.Date;

public class DeliveryAssignment implements Comparable<DeliveryAssignment> {

	private String id;
	private LatLng pickupLatLng;
	private String pickupAddress;
	private LatLng dropoffLatLng;
	private String dropoffAddress;
	private Date date;
	private DeliveryDirections directions;
	private boolean hasPackage;

	public DeliveryAssignment(String id, LatLng pickupLatLng, String pickupAddress, LatLng dropoffLatLng, String dropoffAddress,
			Date date) {
		this.id = id;
		this.pickupLatLng = pickupLatLng;
		this.pickupAddress = pickupAddress;
		this.dropoffLatLng = dropoffLatLng;
		this.dropoffAddress = dropoffAddress;
		this.date = date;
		this.hasPackage = false;
	}

	public String getId() {
		return id;
	}

	public Date getDate() {
		return date;
	}

	public LatLng getPickupLatLng() {
		return pickupLatLng;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public LatLng getDropoffLatLng() {
		return dropoffLatLng;
	}

	public String getDropoffAddress() {
		return dropoffAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DeliveryAssignment other = (DeliveryAssignment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(DeliveryAssignment o) {
		return this.date.compareTo(o.getDate());
	}

	public DeliveryDirections getDirections() {
		return directions;
	}

	public void setDirections(DeliveryDirections directions) {
		this.directions = directions;
	}

	public boolean getHasPackage() {
		return hasPackage;
	}

	public void setHasPackage(boolean hasPackage) {
		this.hasPackage = hasPackage;
	}
	
}
