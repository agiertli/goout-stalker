package org.goout.stalker.service.goout;

import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.output.ThresholdingOutputStream;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.Event;
import org.goout.stalker.model.EventsByArtists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class GoOutService {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String URL_BASE = "https://goout.net/services/feeder/v1/events.json?source=goout&keywords=%s&language=en&unapproved=false&clearDomain=true";
	private static final String GOOUT_HOMEPAGE = "http://goout.net";
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public GoOutService() {
	}

	private Boolean isRelevant = false;

	public EventsByArtists getEvents(ArtistList artists, String city) {

		EventsByArtists events = new EventsByArtists();

		// GoOut API doesn't accept multiple artist in single query
		// So we need to query one after one
		artists.getArtists().forEach(a -> {

			try {

				Response response = ClientBuilder.newBuilder().build()
						.target(new URL(String.format(URL_BASE, a)).toExternalForm())
						.request(MediaType.APPLICATION_JSON_TYPE).buildGet().invoke();

				String json = response.readEntity(String.class);
				Set<Event> eventsByArtist = transformToEvent(a, json, city);
				events.addEvents(a, eventsByArtist);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		});

		return events;
	}

	public Set<Event> transformToEvent(String artist, String json, String city) {

		JsonReader jsonReader = Json.createReader(new StringReader(json));
		JsonObject object = jsonReader.readObject();
		JsonArray schedule = object.getJsonArray("schedule");
		JsonObject events = object.getJsonObject("events");
		JsonObject performers = object.getJsonObject("performers");

		Iterator<JsonValue> it = schedule.iterator();

		Set<Event> artistEvents = new HashSet<Event>();

		while (it.hasNext()) {
			JsonObject s = (JsonObject) it.next();
			String eventCity = s.getJsonObject("venueLocality").getString("name");

			if (city != null) { // filter by city

				if (!eventCity.toLowerCase().contains(city.toLowerCase())) {
					continue; // let's ignore the event outside the desired city
				}
			}

			JsonNumber eventId = s.getJsonNumber("eventId");
			String eventName = events.getJsonObject(eventId.toString()).getString("name");

			isRelevant = false; // filter out some fake results

			JsonArray performerIds = s.getJsonArray("performerIds");
			performerIds.forEach(pid -> {
				JsonObject singlePerformer = performers.getJsonObject(pid.toString());
				String performerName = singlePerformer.getString("name");
				if (performerName.toLowerCase().contains(artist.toLowerCase())) {
					isRelevant = true;
					logger.debug("performer match found %s vs %s" + artist, performerName);

				}

			});

			if (isRelevant) {
				Event event = Event.builder().withName(eventName).withUrl(GOOUT_HOMEPAGE + s.getString("url"))
						.withCity(eventCity).withStart(LocalDateTime.parse(s.getString("start"), DATE_TIME_FORMAT))
						.withVenue(object.getJsonObject("venues").getJsonObject(s.getJsonNumber("venueId").toString())
								.getString("name"))
						.withEventId(eventId.toString()).build();
				artistEvents.add(event);
			}

		}

		jsonReader.close();
		return artistEvents;

	}

}
