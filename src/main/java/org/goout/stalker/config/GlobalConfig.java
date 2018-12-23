package org.goout.stalker.config;

import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GlobalConfig {

	private String URL_BASE = "https://goout.net/services/feeder/v1/events.json?source=goout&keywords=%s&language=en&unapproved=false&clearDomain=true";
	private String GOOUT_HOMEPAGE = "http://goout.net";
	private DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private String mail_subject = "Go Out Stalker Notification";

	@Inject
	@ConfigProperty(name = "SMTP_SERVER")
	private String smtp_server;

	@Inject
	@ConfigProperty(name = "MAIL_USERNAME")
	private String mailUser;

	@Inject
	@ConfigProperty(name = "SMTP_PORT")
	private String smtp_port;

	@Inject
	@ConfigProperty(name = "MAIL_PASSWORD")
	private String mailPassword;

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

	@Inject
	@ConfigProperty(name = "DB_HOST")
	private String host;

	@Inject
	@ConfigProperty(name = "GO_OUT_CITY")
	private String city;
	@Inject
	@ConfigProperty(name = "ARTIST_COL_NAME")
	private String colName;

	@Inject
	@ConfigProperty(name = "TIMER_INTERVAL")
	private String interval;

	@Inject
	@ConfigProperty(name = "INIT_ARTIST_LIST")
	private String initArtists;

	@Inject
	@ConfigProperty(name = "TESTING")
	private Boolean testing;

	public String MAIL_SUBJECT() {
		return mail_subject;
	}

	public String SMTP_SERVER() {

		return smtp_server;
	}

	public String SMTP_PORT() {
		return smtp_port;
	}

	public String MAIL_USERNAME() {

		return mailUser;
	}

	public String MAIL_PASSWORD() {
		return mailPassword;
	}

	public String DB_USERNAME() {
		return username;
	}

	public String DB_PASSWORD() {
		return password;
	}

	public String DB_PORT() {
		return port;
	}

	public String DB_NAME() {
		return dbName;
	}

	public String DB_HOST() {
		return host;
	}

	public String INIT_ARTIST_LIST() {

		return initArtists;
	}

	public Boolean TESTING() {
		return testing;
	}

	public String TIMER_INTERVAL() {
		return interval;
	}

	public String URL_BASE() {
		return URL_BASE;
	}

	public String GOOUT_HOMEPAGE() {
		return GOOUT_HOMEPAGE;
	}

	public DateTimeFormatter DATE_TIME_FORMAT() {
		return DATE_TIME_FORMAT;
	}

	public String GO_OUT_CITY() {
		return city;
	}

	public String ARTIST_COL_NAME() {
		return colName;
	}

}
