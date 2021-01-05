package com.redhat.appdev.courie.driver.data;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import io.quarkus.redis.client.RedisClient;

public class DriverRepositoryFactory {

	
	@Produces @InMemory
	@ApplicationScoped
	public DriverRepository createInMemoryDriverRepo() {
		return new InMemoryDriverRepository();
	}
	
	@Produces @Redis
	@ApplicationScoped
	public DriverRepository createRedisDriverRepo(RedisClient client) {
		return new RedisDriverRepository(client);
	}
}
