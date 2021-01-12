package com.redhat.appdev.courie.driver;

import java.util.HashMap;
import java.util.Map;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

public class KafkaTestDependency implements QuarkusTestResourceLifecycleManager {

	@Override
	public Map<String, String> start() {
		Map<String, String> env = new HashMap<>();
	    env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory("driver-assigned"));
	    env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory("delivery-started"));
	    env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory("delivery-pickedup"));
	    env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory("delivery-droppedoff"));
	    env.putAll(InMemoryConnector.switchOutgoingChannelsToInMemory("driver-location"));
	    return env;
	}

	@Override
	public void stop() {
		InMemoryConnector.clear();
	}
}
