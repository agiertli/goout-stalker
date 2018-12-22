package org.goout.stalker.service.goout;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.db.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class GoOutTimerService {

	@EJB
	private DBService dbService;
	@Resource
	private TimerService timerService;

	@EJB
	private GoOutService goOutService;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Inject
	@ConfigProperty(name = "TIMER_INTERVAL")
	private String interval;

	@Inject
	@ConfigProperty(name = "ARTIST_COL_NAME")
	private String colName;

	@Inject
	@ConfigProperty(name = "GO_OUT_CITY")
	private String city;

	@Inject
	@ConfigProperty(name = "TESTING")
	private Boolean testing;

	public GoOutTimerService() {
	}

	@Timeout
	public void checkGoOutEvents(Timer timer) {
		logger.info("Timer fired - connecting to GoOut");
		ArtistList artists = dbService.findAllArtists(colName);
		EventsByArtists events = goOutService.getEvents(artists, city);
		
		// TODO : Send email with the events

	}

	@PostConstruct
	public void init() {

		ScheduleExpression se = new ScheduleExpression();
		// For testing purposes, let's use SECONDS, in prod, let's use hours
		if (testing) {
			se.hour("*").minute("*").second("0/" + interval);
		} else {

			se.hour("*/" + interval).minute("0").second(0);

		}
		timerService.createCalendarTimer(se, new TimerConfig("EJB timer service timeout at ", false));
	}

	@PreDestroy
	public void stop() {
		System.out.println("EJB Timer: Stop timers.");
		for (Timer timer : timerService.getTimers()) {
			System.out.println("Stopping timer: " + timer.getInfo());
			timer.cancel();
		}
	}
}
