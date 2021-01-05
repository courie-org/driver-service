package com.redhat.appdev.courie.driver.http;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.service.DriverService;

@ApplicationScoped@Path("/drivers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DriversResource {

	private DriverService driverService;
	
	@Inject
	public DriversResource(DriverService driverService) {
		this.driverService = driverService;
	}
	
	@GET
	public Response getDrivers() {
		return Response.ok(this.driverService.getAllDrivers()).build();
	}
	
	@GET
	@Path("/{driverId}")
	public Response getDriver(@PathParam("driverId") String driverId) {
		
		Response response = Response.status(Status.NOT_FOUND).build();
		Optional<Driver> driver = this.driverService.getDriverBy(driverId);
		
		if (driver.isPresent()) {
			response = Response.ok(driver.get()).build();
		}
		
		return response;
	}
	
	@POST
	public Response newDriver(NewDriverRequest newDriverRequest) {
		
		Driver driver = new Driver(newDriverRequest.getId(), newDriverRequest.getName(), 
				newDriverRequest.getLicenseNumber(), newDriverRequest.getCarDescription(), 
				newDriverRequest.getCurrentLatLng());
		
		this.driverService.newDriver(driver);
		return Response.ok(driver).build();
	}
	
	@POST
	@Path("/{driverId}/assignments/{deliveryId}")
	public Response acceptDelivery(@PathParam("driverId") String driverId, 
			@PathParam("deliveryId") String deliveryId) {
		
		this.driverService.acceptDelivery(driverId, deliveryId);
		return Response.accepted().build();
	}
	
	@POST
	@Path("/{driverId}/assignments/{deliveryId}/start")
	public Response start(@PathParam("driverId") String driverId,
			@PathParam("deliveryId") String deliveryId) {
		
		
		this.driverService.start(driverId, deliveryId);
		return Response.accepted().build();
	}
	
	@POST
	@Path("/{driverId}/assignments/{deliveryId}/pickup")
	public Response pickup(@PathParam("driverId") String driverId,
			@PathParam("deliveryId") String deliveryId) {
		
		this.driverService.pickup(driverId, deliveryId);
		return Response.accepted().build();
	}
	
	@POST
	@Path("/{driverId}/assignments/{deliveryId}/dropoff")
	public Response dropoff(@PathParam("driverId") String driverId,
			@PathParam("deliveryId") String deliveryId) {
		
		this.driverService.dropoff(driverId, deliveryId);
		return Response.accepted().build();
	}
}
