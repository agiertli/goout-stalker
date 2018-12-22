package org.goout.stalker.model;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class EventsByArtists {

	private Map<String, Set<Event>> events;

	public EventsByArtists() {

		this.events = new HashMap<String, Set<Event>>();
	}

	public void addEvents(String artist, Set<Event> events) {

		this.events.put(artist, events);
	}

	public Set<Event> getEventsByArtist(String artist) {

		return events.get(artist);
	}

	public Map<String, Set<Event>> getAllEvents() {
		return events;
	}

	@Override
	public String toString() {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		events.keySet().forEach(key -> {

			objectBuilder.add(key, this.getEventsByArtist(key).toString());
		});
		JsonObject obj = objectBuilder.build();
		Writer writer = new StringWriter();
		Json.createWriter(writer).write(obj);
		return writer.toString();
	}

}
