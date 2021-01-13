package com.redhat.appdev.courie.driver.data;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

import io.quarkus.arc.DefaultBean;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.redis.client.RedisClient;

@Dependent
public class DriverRepositoryConfiguration {

	@Produces
	@IfBuildProfile("test")
	public DriverRepository inMemoryDriverRepo() {
		return new InMemoryDriverRepository();
	}
	
	@Produces
	@DefaultBean
	public DriverRepository redisDriverRepo(RedisClient client) {
		return new RedisDriverRepository(client);
	}
}
