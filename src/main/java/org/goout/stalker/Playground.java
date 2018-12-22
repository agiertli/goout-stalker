package org.goout.stalker;

import java.util.HashSet;
import java.util.Set;

import javax.json.Json;

import org.goout.stalker.model.ArtistList;

public class Playground {

	public static void main(String[] args) {

		Set<String> artists = new HashSet<String>();
		artists.add("Aurora");
		ArtistList artistList = new ArtistList(artists);
		
		
	}

}
