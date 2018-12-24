package org.goout.stalker.model;

import java.util.Set;

public class ArtistList {

	private String collectionName;

	public ArtistList() {
	}

	public ArtistList(Set<String> artists) {
		this.artists = artists;
	}

	private Set<String> artists;

	public Set<String> getArtists() {
		return artists;
	}

	public void setArtists(Set<String> artists) {
		this.artists = artists;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artists == null) ? 0 : artists.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArtistList other = (ArtistList) obj;
		if (artists == null) {
			if (other.artists != null)
				return false;
		} else if (!artists.equals(other.artists))
			return false;
		return true;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public String toString() {
		return "ArtistList [artists=" + artists + "]";
	}
}
