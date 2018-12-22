package org.goout.stalker.rest;

import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.db.DBService;
import org.goout.stalker.service.goout.GoOutService;

@Path("/")
public class GoOutStalkerResource {

	@Inject
	@ConfigProperty(name = "ARTIST_COL_NAME")
	private String colName;

	@EJB
	private DBService dbService;

	@EJB
	private GoOutService goOutService;

	@Inject
	@ConfigProperty(name = "GO_OUT_CITY")
	private String city;

	@Path("/artists/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addArtist(Set<String> artists) {

		dbService.addArtists(new ArtistList(artists), colName);

		return Response.ok().build();
	}

	@Path("/artists/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllArtists() {
		return Response.ok(dbService.findAllArtists(colName).getArtists()).build();

	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/events/all")
	public Response getAllEvents() {

		ArtistList artists = dbService.findAllArtists(colName);
		EventsByArtists events = goOutService.getEvents(artists, city);

		return Response.ok(events).build();
	}

}
