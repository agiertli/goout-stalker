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

import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.db.DBService;
import org.goout.stalker.service.goout.GoOutService;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/")
@Api(value = "/", tags = "goout-stalker-api")
@SwaggerDefinition(tags = {
	    @Tag(name = "goout-stalker-api", description = "Go Out Stalker REST API")
	})
public class GoOutStalkerResource {

	@EJB
	private DBService dbService;

	@EJB
	private GoOutService goOutService;

	@Inject
	private GlobalConfig config;

	@Path("/artists/add")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addArtist(Set<String> artists) {

		dbService.addArtists(new ArtistList(artists), config.ARTIST_COL_NAME());

		return Response.ok().build();
	}

	@Path("/artists/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllArtists() {
		return Response.ok(dbService.findAllArtists(config.ARTIST_COL_NAME()).getArtists()).build();

	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/events/all")
	public Response getAllEvents() {

		ArtistList artists = dbService.findAllArtists(config.ARTIST_COL_NAME());
		EventsByArtists events = goOutService.getEvents(artists, config.GO_OUT_CITY());

		return Response.ok(events).build();
	}

}
