package org.goout.stalker;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.Event;
import org.goout.stalker.service.goout.AsyncGoOutSupplier;
import org.goout.stalker.service.goout.GoOutService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)
public class ConcurrentRestTest {

	@Inject
	private GoOutService service;



	@Test
	public void testParallelRest() throws InterruptedException, ExecutionException {

		Set<String> input = new HashSet<String>();
		input.add("dné");
		input.add("Aurora");
		input.add("Jedi Mind Tricks");
		input.add("Jessie");
		input.add("LP");

		ArtistList artists = new ArtistList(input);

		long start = System.currentTimeMillis();
		service.getEvents(artists, "Prague");
		long serialDuration = (System.currentTimeMillis() - start);
		System.out.println("The sync api call took:" + serialDuration);
		
		start = System.currentTimeMillis();
		service.getEventsAsync(artists, "Prague");
		long asyncDuration = (System.currentTimeMillis() - start);
		System.out.println("The async api call took:" + asyncDuration);
		
		assertTrue(asyncDuration < serialDuration); //otherwise it doens't make sense

	}

}
