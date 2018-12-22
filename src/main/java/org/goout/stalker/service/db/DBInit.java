package org.goout.stalker.service.db;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.model.ArtistList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class DBInit {

	@EJB
	private DBService dbService;
	private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Inject
	@ConfigProperty(name = "INIT_ARTIST_LIST")
	private String initArtists;

	@Inject
	@ConfigProperty(name = "ARTIST_COL_NAME")
	private String artistColName;

	@PostConstruct
	public void init() {
		ArtistList artists = new ArtistList(new HashSet<String>(Arrays.asList(initArtists.split(","))));
		logger.info("DB Initialization with artists:" + artists);
		Set<String> filteredArtists = new HashSet<String>();

		artists.getArtists().forEach(a -> {

			if (dbService.findById(a, artistColName) == null) {
				filteredArtists.add(a);
			}

		});

		if (!filteredArtists.isEmpty())
			dbService.addArtists(new ArtistList(filteredArtists), artistColName);

	}

}
