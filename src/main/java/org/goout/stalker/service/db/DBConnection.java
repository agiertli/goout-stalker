package org.goout.stalker.service.db;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.model.ArtistList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoBulkWriteException;
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
	@ConfigProperty(name = "DB_HOST")
	private String host;

	@Inject
	@ConfigProperty(name = "DB_PORT")
	private String port;

	@Inject
	@ConfigProperty(name = "DB_NAME")
	private String dbName;

	@Inject
	@ConfigProperty(name = "DB_USERNAME")
	private String username;

	@Inject
	@ConfigProperty(name = "DB_PASSWORD")
	private String password;

	@PostConstruct
	public void connect() {

		logger.info("connecting to mongo db");

		MongoCredential credential = MongoCredential.createCredential(username, dbName, password.toCharArray());
		client = new MongoClient(new ServerAddress(host, Integer.valueOf(port)), credential,
				MongoClientOptions.builder().build());
		db = client.getDatabase(dbName);

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
