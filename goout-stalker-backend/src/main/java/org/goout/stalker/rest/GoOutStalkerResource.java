package org.goout.stalker.rest;

import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.goout.stalker.JSONUtil;
import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.EmailConfig;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.db.DBService;
import org.goout.stalker.service.email.ConnectionError;
import org.goout.stalker.service.email.EmailService;
import org.goout.stalker.service.goout.GoOutService;
import org.goout.stalker.service.goout.GoOutTimerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@Path("/")
@Api(value = "/", tags = "goout-stalker-api")
@SwaggerDefinition(tags = { @Tag(name = "goout-stalker-api", description = "Go Out Stalker REST API") })
public class GoOutStalkerResource {

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	@EJB
	private JSONUtil jsonUtil;

	@EJB
	private DBService dbService;

	@EJB
	private GoOutService goOutService;

	@EJB
	private EmailService emailService;

	@Inject
	private GlobalConfig config;

	@EJB
	private GoOutTimerService goOutTimerService;

	@Path("/artists")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Add artist", notes = "Add 1 to N artist to watch for", response = Set.class)
	public Response addArtist(Set<String> artists) {

		dbService.addArtists(new ArtistList(artists), config.ARTIST_COL_NAME());

		return Response.ok().build();
	}

	@DELETE
	@Path("/artists")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Delete artist from DB", notes = "Remove 1 to N artist to watch for", response = Long.class)

	public Response deleteArtist(Set<String> artists) {

		Long deleteCount = dbService.removeArtists(new ArtistList(artists), config.ARTIST_COL_NAME());

		return Response.ok(deleteCount).build();

	}

	@Path("/artists/all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find all artists", notes = "Returns a list containing all artists", response = Set.class)

	public Response getAllArtists() {
		return Response.ok(dbService.findAllArtists(config.ARTIST_COL_NAME()).getArtists()).build();

	}

	@Produces(MediaType.APPLICATION_JSON)
	@GET
	@Path("/events/all")
	@ApiOperation(value = "Get all events", notes = "Returns all events by all artists", response = EventsByArtists.class)
	public Response getAllEvents() {

		ArtistList artists = dbService.findAllArtists(config.ARTIST_COL_NAME());
		EventsByArtists events = goOutService.getEvents(artists, config.GO_OUT_CITY());

		return Response.ok(jsonUtil.convertToJson(events)).build();
	}

	@POST
	@Path("/email/start")
	@ApiOperation(value = "Configure email notifications", notes = "All scheduled emails are cancelled and reschedueld with the new configuration")
	public Response startMailNotifications(EmailConfig emailConfig) throws ConnectionError {

		try {
			emailService.testConnection(emailConfig);
		} catch (ConnectionError e) {

			e.printStackTrace();
			return Response.status(Status.BAD_REQUEST).entity("{\"error\":" + "\"" + "Wrong Email Configuration provided!" + "\"}").build();

		}

		goOutTimerService.stop();

		config.setMailInterval(emailConfig.getInterval());
		config.setMailPswd(emailConfig.getPassword());
		config.setMailUsername(emailConfig.getUsername());
		config.setSmtpPort(emailConfig.getSmtpPort());
		config.setSmtpHost(emailConfig.getSmtpServer());
		config.setnotifications("true");
		config.setCity(emailConfig.getCity());

		goOutTimerService.init();

		return Response.ok("{\"status\" : \"ok\" }").build();
	}

	@POST
	@Path("/email/stop")
	@ApiOperation(value = "Cancels all schedueld emails", notes = "All scheduled emails are cancelled and reschedueld with the new configuration")

	public Response stopEmails() {
		config.setnotifications("false");
		goOutTimerService.stop();
		return Response.ok().build();
	}

	@GET
	@Path("/email")
	@ApiOperation(value = "Returns current email config", response = EmailConfig.class)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentEmailConfig() {

		Boolean enabled = Boolean.valueOf(config.NOTIFICATIONS_ENABLED());

		EmailConfig email = new EmailConfig();

		if (!enabled) {
			email = EmailConfig.builder().withEnabled(enabled).build();

			return Response.ok(email).build();

		} else {
			email = EmailConfig.builder().withEnabled(enabled).withSmtpPort(config.SMTP_PORT())
					.withSmtpServer(config.SMTP_SERVER()).withUsername(config.MAIL_USERNAME())
					.withInterval(config.TIMER_INTERVAL()).withCity(config.GO_OUT_CITY()).build();

			return Response.ok(email).build();

		}

	}

}
