package org.goout.stalker;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;

import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.Event;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.goout.GoOutService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)

public class GoOutIntegrationTest {

	@EJB
	private GoOutService goOutService;

	@Test
	/**
	 * This test will be passing only until 17.1.
	 */
	public void getArtistTestNoFilter() {
		Set<String> artists = new HashSet<String>();
		artists.add("Aurora");
		EventsByArtists events = goOutService.getEvents(new ArtistList(artists), null);

		assertEquals(2, events.getEventsByArtist("Aurora").size());

	}

	@Test
	/**
	 * This test will be passing only until 17.1.
	 */
	public void getArtistTestCityFilter() {
		Set<String> artists = new HashSet<String>();
		artists.add("Aurora");
		EventsByArtists events = goOutService.getEvents(new ArtistList(artists), "Warsaw");

		assertEquals(1, events.getEventsByArtist("Aurora").size());

	}

}
