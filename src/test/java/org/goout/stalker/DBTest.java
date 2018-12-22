package org.goout.stalker;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.service.db.DBConnection;
import org.goout.stalker.service.db.DBService;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

@RunWith(Arquillian.class)
@DefaultDeployment(type = DefaultDeployment.Type.WAR)
public class DBTest {

	@EJB
	private DBService service;

	@EJB
	private DBConnection connection;

	public static final String DB_COL = "TEST_ARTISTS";

	@Before
	public void connect() {

	}

	@Test
	public void testAddArtist() {

		Set<String> artists = new HashSet<String>();
		artists.add("Jedi Mind Tricks");
		artists.add("dné");
		artists.add("Madonna");
		ArtistList artistList = new ArtistList(artists);
		service.addArtists(artistList, DB_COL);

		assertEquals(artists.size(), connection.getDb().getCollection(DB_COL).count());
	}

	@Test
	public void testFindAll() {

		Set<String> artists = new HashSet<String>();
		artists.add("Jedi Mind Tricks");
		artists.add("dné");
		artists.add("Madonna");
		ArtistList artistList = new ArtistList(artists);
		service.addArtists(artistList, DB_COL);

		assertEquals(artistList, service.findAllArtists(DB_COL));
	}

	@Test
	public void testDeleteArtist() {
		Set<String> addMe = new HashSet<String>();
		addMe.add("Jedi Mind Tricks");
		addMe.add("dné");
		addMe.add("Madonna");
		ArtistList artistList = new ArtistList(addMe);
		service.addArtists(artistList, DB_COL);
		assertEquals(addMe.size(), connection.getDb().getCollection(DB_COL).count());

		Set<String> removeMe = new HashSet<String>();
		removeMe.add("Jedi Mind Tricks");
		artistList = new ArtistList(removeMe);
		service.removeArtists(artistList, DB_COL);
		assertEquals(addMe.size() - removeMe.size(), connection.getDb().getCollection(DB_COL).count());

	}

	@After
	public void tearDown() {
		connection.getDb().getCollection(DB_COL).drop();
	}

}
