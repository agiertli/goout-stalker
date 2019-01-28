package org.goout.stalker;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import org.goout.stalker.model.Event;
import org.goout.stalker.model.EventsByArtists;

@Stateless
public class JSONUtil {

	public String convertToJson(EventsByArtists events) {

		JsonObjectBuilder rootJson = Json.createObjectBuilder();

		events.getAllEvents().keySet().stream().filter(a -> !events.getEventsByArtist(a).isEmpty()).forEach(a -> {
			Set<Event> filteredEvents = events.getEventsByArtist(a);
			JsonArrayBuilder artistEvents = Json.createArrayBuilder();

			filteredEvents.forEach(e -> {
				JsonObjectBuilder specificEvent = Json.createObjectBuilder();
				specificEvent.add("city", e.getCity());
				specificEvent.add("name", e.getName());
				specificEvent.add("date", e.getStart().toString());
				specificEvent.add("url", e.getUrl());
				specificEvent.add("venue", e.getVenue());

				artistEvents.add(specificEvent);
			});

			JsonArray obj = artistEvents.build();
			rootJson.add(a, obj);
		});

		Map<String, Boolean> config = new HashMap<>();
		config.put(JsonGenerator.PRETTY_PRINTING, true);
		JsonWriterFactory jwf = Json.createWriterFactory(config);
		StringWriter sw = new StringWriter();
		try (JsonWriter jsonWriter = jwf.createWriter(sw)) {
			jsonWriter.writeObject(rootJson.build());
		}

		return sw.toString();
	}

}
