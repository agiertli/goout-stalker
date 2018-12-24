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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventsByArtists other = (EventsByArtists) obj;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		return true;
	}

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
