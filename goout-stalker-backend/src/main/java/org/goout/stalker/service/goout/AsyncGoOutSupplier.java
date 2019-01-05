package org.goout.stalker.service.goout;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Supplier;

import org.goout.stalker.model.Event;

public class AsyncGoOutSupplier<Set> implements Supplier<java.util.Set<Event>> {
	
	public AsyncGoOutSupplier() {
		
	}

	@Override
	public java.util.Set<Event> get() {
		
		byte[] array = new byte[7]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));
		java.util.Set<Event> result = new HashSet<Event>();
		Event e = Event.builder().withCity(generatedString).build();
		result.add(e);

		return result;
	}

}
