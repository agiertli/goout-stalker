package org.goout.stalker.service.email;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.Event;
import org.goout.stalker.model.EventsByArtists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EmailService {

	@Inject
	private GlobalConfig config;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public void send(EventsByArtists events) {

		String body = prepareBody(events);

		String username = config.MAIL_USERNAME();
		String password = config.MAIL_PASSWORD();

		Properties props = new Properties();
		props.put("mail.smtp.host", config.SMTP_SERVER());
		props.put("mail.smtp.socketFactory.port", config.SMTP_PORT());
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", config.SMTP_PORT());

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			// message.set
			message.setFrom(new InternetAddress(config.MAIL_USERNAME()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(config.MAIL_USERNAME()));
			message.setSubject(config.MAIL_SUBJECT());
			message.setText(body);

			Transport.send(message);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}

	public String prepareBody(EventsByArtists events) {

		String body = "Hi,\n here are some new events for you. \n";

		JsonObjectBuilder rootJson = Json.createObjectBuilder();

		events.getAllEvents().keySet().forEach(artist -> {

			if (!events.getEventsByArtist(artist).isEmpty()) {

				Set<Event> filteredEvents = events.getEventsByArtist(artist);
				JsonArrayBuilder artistEvents = Json.createArrayBuilder();
				for (Event e : filteredEvents) {

					JsonObjectBuilder specificEvent = Json.createObjectBuilder();
					specificEvent.add("city", e.getCity());
					specificEvent.add("name", e.getName());
					specificEvent.add("date", e.getStart().toString());
					specificEvent.add("url", e.getUrl());
					specificEvent.add("venue", e.getVenue());

					artistEvents.add(specificEvent);
				}
				JsonArray obj = artistEvents.build();
				rootJson.add(artist, obj);

			}
		});

		Map<String, Boolean> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonWriterFactory jwf = Json.createWriterFactory(config);
		StringWriter sw = new StringWriter();
		try (JsonWriter jsonWriter = jwf.createWriter(sw)) {
			jsonWriter.writeObject(rootJson.build());
		}

		return body + sw.toString();
	}

}
