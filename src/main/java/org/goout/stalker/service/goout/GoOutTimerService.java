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

import org.goout.stalker.JSONUtil;
import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.ArtistList;
import org.goout.stalker.model.EventsByArtists;
import org.goout.stalker.service.db.DBService;
import org.goout.stalker.service.email.EmailService;
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
	private EmailService email;

	@EJB
	private GoOutService goOutService;

	@Inject
	private GlobalConfig config;

	@EJB
	private JSONUtil jsonUtil;

	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public GoOutTimerService() {
	}

	@Timeout
	public void checkGoOutEvents(Timer timer) {
		logger.info("Timer fired - connecting to GoOut");
		ArtistList artists = dbService.findAllArtists(config.ARTIST_COL_NAME());
		EventsByArtists events = goOutService.getEvents(artists, config.GO_OUT_CITY());

		String pastNotification = dbService.findById(String.valueOf(events.hashCode()), config.NOTIFICATION_COL_NAME());
		// either we have a new set of events of we are adding the first set of events
		// ever

		logger.info("past notification:" + pastNotification);
		logger.info("notification count" + dbService.getNotificationIdCount());
		if (pastNotification == null || dbService.getNotificationIdCount() == 0L) {

			logger.info("New events found - sending email notification");
			dbService.removeNotificationId();
			dbService.insertNotificationId(String.valueOf(events.hashCode()));
			// email.send(events);
		} else {
			logger.info("No new events found - not sending an email");
		}

	}

	@PostConstruct
	public void init() {

		if (config.NOTIFICATIONS_ENABLED().equals("true")) {

			if (!config.SMTP_PORT().equals("N/A") && !config.SMTP_SERVER().equals("N/A")
					&& !config.MAIL_PASSWORD().equals("N/A") && !config.MAIL_USERNAME().equals("N/A")) {
				logger.info("Enabling email notifications");

				ScheduleExpression se = new ScheduleExpression();
				// For testing purposes, let's use SECONDS, in prod, let's use hours
				if (config.TESTING()) {
					se.hour("*").minute("*").second("*/" + config.TIMER_INTERVAL());
				} else {

					se.hour("*/" + config.TIMER_INTERVAL()).minute("0").second(0);

				}

				timerService.createCalendarTimer(se, new TimerConfig("EJB timer service timeout at ", false));
			} else {
				logger.warn(
						"Email notifications not enabled due to missing configuration values - please add smtp host, port, username and password");
			}
		} else {

			logger.info("Email notifications disabled");
		}
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
