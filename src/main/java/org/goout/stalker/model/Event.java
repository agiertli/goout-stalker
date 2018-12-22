package org.goout.stalker.model;

import java.io.StringWriter;
import java.io.Writer;
import java.time.LocalDateTime;

import javax.annotation.Generated;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Event {

	private String name;

	private String eventId;

	private String url;

	private String city;

	private LocalDateTime start;

	private String venue;

	@Generated("SparkTools")
	private Event(Builder builder) {
		this.name = builder.name;
		this.eventId = builder.eventId;
		this.url = builder.url;
		this.city = builder.city;
		this.start = builder.start;
		this.venue = builder.venue;
	}

	/**
	 * Creates builder to build {@link Event}.
	 * 
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Event}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private String name;
		private String eventId;
		private String url;
		private String city;
		private LocalDateTime start;
		private String venue;

		private Builder() {
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withEventId(String eventId) {
			this.eventId = eventId;
			return this;
		}

		public Builder withUrl(String url) {
			this.url = url;
			return this;
		}

		public Builder withCity(String city) {
			this.city = city;
			return this;
		}

		public Builder withStart(LocalDateTime start) {
			this.start = start;
			return this;
		}

		public Builder withVenue(String venue) {
			this.venue = venue;
			return this;
		}

		public Event build() {
			return new Event(this);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	@Override
	public String toString() {

		JsonObjectBuilder objectBuilder = Json.createObjectBuilder().add("event-name", name).add("eventId", eventId)
				.add("url", url).add("city", city).add("start-date", start.toString()).add("venue", venue);
		JsonObject obj = objectBuilder.build();
		Writer writer = new StringWriter();
		Json.createWriter(writer).write(obj);
		return writer.toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eventId == null) ? 0 : eventId.hashCode());
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
		Event other = (Event) obj;
		if (eventId == null) {
			if (other.eventId != null)
				return false;
		} else if (!eventId.equals(other.eventId))
			return false;
		return true;
	}

}
