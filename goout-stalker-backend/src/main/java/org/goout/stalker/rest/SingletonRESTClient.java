package org.goout.stalker.rest;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import org.goout.stalker.config.GlobalConfig;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

@Singleton
@Startup
public class SingletonRESTClient {

	@Inject
	private GlobalConfig config;
	private ResteasyClient client;

	@Resource
	private ManagedExecutorService managedExecutorService;

	@PostConstruct
	public void init() {

		client = new ResteasyClientBuilder()
				.connectionPoolSize(Integer.valueOf(config.REST_CLIENT_CONNECTION_POOL_SIZE()))
				.asyncExecutor(managedExecutorService).build();
		
	}

	public ResteasyClient getClient() {
		return client;
	}
	
	@PreDestroy
	public void shutdown() {
		this.client.close();
	}

}
