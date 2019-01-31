package org.goout.stalker.service.db;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.goout.stalker.config.GlobalConfig;
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
	private GlobalConfig config;

	@PostConstruct
	public void init() {
		
		//Persist the initial artists passed via env/system property
		
		System.out.println("Encoding:"+System.getProperty("file.encoding"));
		System.out.println("Charset:"+Charset.defaultCharset());
		
		ArtistList artists = new ArtistList(new HashSet<String>(Arrays.asList(config.INIT_ARTIST_LIST().split(","))));
		logger.info("DB Initialization with artists:" + artists);
		Set<String> filteredArtists = new HashSet<String>();

		artists.getArtists().forEach(a -> {

			if (dbService.findById(a, config.ARTIST_COL_NAME()) == null) {
				filteredArtists.add(a);
			}

		});

		if (!filteredArtists.isEmpty())
			dbService.addArtists(new ArtistList(filteredArtists), config.ARTIST_COL_NAME());
	

	}

}
