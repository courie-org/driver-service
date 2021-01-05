package com.redhat.appdev.courie.driver.location;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DriverLocationEncoder implements Encoder.Text<DriverLocation> {

	@Override
	public void init(EndpointConfig config) { }

	@Override
	public void destroy() { }

	@Override
	public String encode(DriverLocation object) throws EncodeException {
		
		ObjectMapper mapper = new ObjectMapper();
		String jsonValue = "";
		try {
			jsonValue = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return jsonValue;
	}

}
