package com.redhat.appdev.courie.driver.service;

import java.util.concurrent.CompletionStage;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient
public interface DeliveryApiClient {

	@GET
	@Path("/deliveries/{deliveryId}")
	@Consumes("application/json")
	public CompletionStage<DeliveryResponse> getDelivery(@PathParam("deliveryId") String deliveryId);
}
