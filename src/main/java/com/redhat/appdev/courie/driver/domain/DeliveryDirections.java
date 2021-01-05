package com.redhat.appdev.courie.driver.domain;

import java.util.ArrayList;
import java.util.List;

public class DeliveryDirections {
	
	private List<LatLng> steps = new ArrayList<>();
	private String polyline;
	
	public DeliveryDirections() { }

	public DeliveryDirections(List<LatLng> steps, String polyline) {
		this.steps = steps;
		this.polyline = polyline;
	}

	public void addStep(LatLng point) {
		steps.add(point);
	}

	public LatLng nextLocation() {
		if (!isFinished()) {
			return steps.remove(0);
		}
		else {
			return null;
		}
	}
	
	public boolean isFinished() {
		return steps.isEmpty();
	}

	public String getPolyline() {
		return polyline;
	}

	public void setPolyline(String polyline) {
		this.polyline = polyline;
	}

	public List<LatLng> getSteps() {
		return steps;
	}
	
}
