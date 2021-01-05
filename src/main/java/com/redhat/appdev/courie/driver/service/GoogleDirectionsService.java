package com.redhat.appdev.courie.driver.service;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.LatLng;
import com.redhat.appdev.courie.driver.domain.DeliveryDirections;

@ApplicationScoped
public class GoogleDirectionsService implements DirectionsService {

	private GeoApiContext context;
	
	@Inject
	public GoogleDirectionsService(@ConfigProperty(name = "driver.directions.apiKey") String apiKey) {
		this.context = new GeoApiContext.Builder()
				.apiKey(apiKey)
				.build();
	}
	
	@Override
	public DeliveryDirections calculateDirections(com.redhat.appdev.courie.driver.domain.LatLng driver, 
			com.redhat.appdev.courie.driver.domain.LatLng pickup,
			com.redhat.appdev.courie.driver.domain.LatLng dropoff) {
		
		DeliveryDirections directions = new DeliveryDirections();
		
		try {
			System.out.println(context);
			DirectionsResult result = DirectionsApi.newRequest(context)
				.origin(new LatLng(Double.parseDouble(driver.getLat()), Double.parseDouble(driver.getLng())))
				.destination(new LatLng(Double.parseDouble(dropoff.getLat()), Double.parseDouble(dropoff.getLng())))
				.waypoints(new LatLng(Double.parseDouble(pickup.getLat()), Double.parseDouble(pickup.getLng())))
				.await();
			
			if (result.routes.length <= 0)
				throw new RuntimeException("No routes found!");
			
			for (LatLng point : result.routes[0].overviewPolyline.decodePath()) {
				directions.addStep(new com.redhat.appdev.courie.driver.domain.LatLng(String.valueOf(point.lat), 
						String.valueOf(point.lng)));
			}
			
			directions.setPolyline(result.routes[0].overviewPolyline.getEncodedPath());
			
		} catch (ApiException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
		
		return directions;
	}

}
