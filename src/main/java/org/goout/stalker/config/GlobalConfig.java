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
	private String EMAIL_INTRO = "Hi,\n here are some new events for you. \n";

	@Inject
	@ConfigProperty(name = "NOTIFICATIONS_ENABLED")
	private String notifications;

	@Inject
	@ConfigProperty(name = "SMTP_SERVER", defaultValue = "N/A")
	private String smtp_server;

	@Inject
	@ConfigProperty(name = "MAIL_USERNAME", defaultValue = "N/A")
	private String mailUser;

	@Inject
	@ConfigProperty(name = "SMTP_PORT", defaultValue = "N/A")
	private String smtp_port;

	@Inject
	@ConfigProperty(name = "MAIL_PASSWORD", defaultValue = "N/A")
	private String mailPassword;

	@Inject
	@ConfigProperty(name = "DB_PORT", defaultValue="27017")
	private String port;

	@Inject
	@ConfigProperty(name = "MONGODB_DATABASE")
	private String dbName;

	@Inject
	@ConfigProperty(name = "MONGODB_USER")
	private String username;

	@Inject
	@ConfigProperty(name = "MONGODB_PASSWORD")
	private String password;

	@Inject
	@ConfigProperty(name = "DB_HOST", defaultValue="mongodb")
	private String host;

	@Inject
	@ConfigProperty(name = "GO_OUT_CITY")
	private String city;

	private final String artistColName = "artists";

	private final String notificationColName = "notificationId";

	@Inject
	@ConfigProperty(name = "TIMER_INTERVAL")
	private String interval;

	@Inject
	@ConfigProperty(name = "INIT_ARTIST_LIST")
	private String initArtists;

	@Inject
	@ConfigProperty(name = "TESTING", defaultValue="false")
	private Boolean testing;

	public String NOTIFICATION_COL_NAME() {
		return notificationColName;
	}

	public String EMAIL_INTRO() {
		return EMAIL_INTRO;
	}

	public String MAIL_SUBJECT() {
		return mail_subject;
	}

	public String NOTIFICATIONS_ENABLED() {

		return notifications;
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
		return artistColName;
	}

}
