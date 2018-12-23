package org.goout.stalker.service.db;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.goout.stalker.config.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

@Startup
@Singleton
public class DBConnection {

	private MongoDatabase db;
	private MongoClient client;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Inject
	private GlobalConfig config;

	@PostConstruct
	public void connect() {

		logger.info("connecting to mongo db");

		MongoCredential credential = MongoCredential.createCredential(config.DB_USERNAME(), config.DB_NAME(),
				config.DB_PASSWORD().toCharArray());
		client = new MongoClient(new ServerAddress(config.DB_HOST(), Integer.valueOf(config.DB_PORT())), credential,
				MongoClientOptions.builder().build());
		db = client.getDatabase(config.DB_NAME());

	}

	@PreDestroy
	public void disconnect() {
		logger.info("disconnecting from the mongo db");
		client.close();
	}

	public MongoDatabase getDb() {
		return db;
	}

	public void setDb(MongoDatabase db) {
		this.db = db;
	}

	public MongoClient getClient() {
		return client;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

}
