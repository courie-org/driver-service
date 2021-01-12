package com.redhat.appdev.courie.driver.http;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.redhat.appdev.courie.driver.domain.Driver;
import com.redhat.appdev.courie.driver.domain.LatLng;
import com.redhat.appdev.courie.driver.service.DriverService;
import com.redhat.appdev.courie.driver.utlis.Utils;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.http.ContentType;

@QuarkusTest
public class DriversResourceTest {

	@InjectMock
	DriverService driverService;
	
	@Test
	public void shouldReturnAllDrivers() {
		Set<Driver> drivers = new HashSet<Driver>();
		drivers.add(Utils.RON);
		
		when(driverService.getAllDrivers()).thenReturn(drivers);
		given()
			.when().get("/drivers")
			.then()
			.statusCode(200)
			.body(is("[{\"id\":\"1\",\"name\":\"Ron Weasley\",\"licenseNumber\":\"cx-wefsd\",\"carDescription\":\"Magical Car\",\"currentLatLng\":{\"lat\":\"10\",\"lng\":\"10\"},\"deliveryHistory\":[],\"assignments\":[],\"currentDelivery\":null,\"available\":true}]"));
	}
	
	@Test
	public void shouldReturnSingleDriverById() {

		when(driverService.getDriverBy(Utils.RON.getId())).thenReturn(Optional.of(Utils.RON));
		given()
		 	.pathParam("id", Utils.RON.getId())
			.when().get("/drivers/{id}")
			.then()
			.statusCode(200)
			.body(is("{\"id\":\"1\",\"name\":\"Ron Weasley\",\"licenseNumber\":\"cx-wefsd\",\"carDescription\":\"Magical Car\",\"currentLatLng\":{\"lat\":\"10\",\"lng\":\"10\"},\"deliveryHistory\":[],\"assignments\":[],\"currentDelivery\":null,\"available\":true}"));
	}
	
	@Test
	public void shouldCreateNewDriver() {
		
		NewDriverRequest newDriverRequest = new NewDriverRequest();
		newDriverRequest.setId("9");
		newDriverRequest.setName("Harry Potter");
		newDriverRequest.setLicenseNumber("TOTL-09");
		newDriverRequest.setCarDescription("Nimbus 2000");
		newDriverRequest.setCurrentLatLng(new LatLng("100", "100"));
		
		given()
			.contentType(ContentType.JSON)
			.body(newDriverRequest)
			.when().post("/drivers")
			.then()
			.statusCode(200)
			.body(is("{\"id\":\"9\",\"name\":\"Harry Potter\",\"licenseNumber\":\"TOTL-09\",\"carDescription\":\"Nimbus 2000\",\"currentLatLng\":{\"lat\":\"100\",\"lng\":\"100\"},\"deliveryHistory\":[],\"assignments\":[],\"currentDelivery\":null,\"available\":true}"));
		
		verify(driverService, times(1)).newDriver(any(Driver.class));
	}
	
	@Test
	public void shouldAcceptDelivery() {
		
		given()
			.contentType(ContentType.JSON)
			.body("")
			.pathParams("driverId", "1", "deliveryId", "1")
			.when().post("/drivers/{driverId}/assignments/{deliveryId}")
			.then()
			.statusCode(202)
			.body(is(""));
		
		verify(driverService, times(1)).acceptDelivery("1", "1");
	}
	
	@Test
	public void shouldStartDelivery() {
		
		given()
			.contentType(ContentType.JSON)
			.body("")
			.pathParams("driverId", "1", "deliveryId", "1")
			.when().post("/drivers/{driverId}/assignments/{deliveryId}/start")
			.then()
			.statusCode(202)
			.body(is(""));
		
		verify(driverService, times(1)).start("1", "1");
	}
	
	@Test
	public void shouldPickupDelivery() {
		
		given()
			.contentType(ContentType.JSON)
			.body("")
			.pathParams("driverId", "1", "deliveryId", "1")
			.when().post("/drivers/{driverId}/assignments/{deliveryId}/pickup")
			.then()
			.statusCode(202)
			.body(is(""));
		
		verify(driverService, times(1)).pickup("1", "1");
	}
	
	@Test
	public void shouldDropoffDelivery() {
		
		given()
			.contentType(ContentType.JSON)
			.body("")
			.pathParams("driverId", "1", "deliveryId", "1")
			.when().post("/drivers/{driverId}/assignments/{deliveryId}/dropoff")
			.then()
			.statusCode(202)
			.body(is(""));
		
		verify(driverService, times(1)).dropoff("1", "1");
	}
}
