package org.goout.stalker.config;

import java.time.format.DateTimeFormatter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class GlobalConfig {

	private final String URL_BASE = "https://goout.net/services/feeder/v1/events.json?source=goout&keywords=%s&language=en&unapproved=false&clearDomain=true";
	private final String GOOUT_HOMEPAGE = "http://goout.net";
	private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private final String mail_subject = "Go Out Stalker Notification";
	private final String EMAIL_INTRO = "Hi,\n here are some new events for you. \n";
	private final String EMAIL_C0L = "emailConfig";

	private final String EMAIL_CONFIG_ID = "unique-email-config-id";
	@Inject
	@ConfigProperty(name = "NOTIFICATIONS_ENABLED", defaultValue = "false")
	private String notifications;

	@Inject
	@ConfigProperty(name = "SMTP_SERVER", defaultValue = "N/A")
	private String smtp_server;

	@Inject
	@ConfigProperty(name = "REST_CLIENT_CONNECTION_POOL_SIZE", defaultValue = "40")
	private String restClientPoolSize;

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
	@ConfigProperty(name = "DB_PORT", defaultValue = "27017")
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
	@ConfigProperty(name = "DB_HOST", defaultValue = "mongodb")
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
	@ConfigProperty(name = "TESTING", defaultValue = "false")
	private Boolean testing;

	public String NOTIFICATION_COL_NAME() {
		return notificationColName;
	}

	public void setGoOutCity(String city) {

		this.city = city;
	}

	public String EMAIL_CONFIG_ID() {

		return EMAIL_CONFIG_ID;
	}

	public String EMAIL_C0L() {

		return EMAIL_C0L;
	}

	public String EMAIL_INTRO() {
		return EMAIL_INTRO;
	}

	public String MAIL_SUBJECT() {
		return mail_subject;
	}

	public void setnotifications(String enabled) {

		this.notifications = enabled;
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

	public void setSmtpHost(String smtpServer) {

		this.smtp_server = smtpServer;
	}

	public void setSmtpPort(String port) {
		this.smtp_port = port;
	}

	public void setMailUsername(String username) {

		this.mailUser = username;
	}

	public void setMailPswd(String password) {
		this.mailPassword = password;
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

	public void setMailInterval(String mailInterval) {
		this.interval = mailInterval;
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

	public void setCity(String city) {
		this.city = city;
	}

	public String ARTIST_COL_NAME() {
		return artistColName;
	}

	public String REST_CLIENT_CONNECTION_POOL_SIZE() {
		return restClientPoolSize;
	}

}
