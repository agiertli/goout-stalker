package org.goout.stalker.service.db;

import static com.mongodb.client.model.Filters.eq;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.bson.Document;
import org.goout.stalker.model.ArtistList;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

@Stateless
public class DBService {

	private Long deleteCount;

	@EJB
	private DBConnection connection;

	public DBService() {
	}

	public long removeArtists(ArtistList artists, String colName) {

		deleteCount = 0L;

		MongoCollection<Document> col = connection.getDb().getCollection(colName);

		artists.getArtists().forEach(a -> {
			col.deleteOne(eq("_id", a));
			deleteCount++;
		});
		return deleteCount;
	}

	public void addArtists(ArtistList artists, String colName) {

		MongoCollection<Document> col = connection.getDb().getCollection(colName);

		List<Document> toBeInserted = new ArrayList<Document>();

		artists.getArtists().forEach(a -> {
			Document doc = new Document().append("_id", a);
			toBeInserted.add(doc);
		});

		col.insertMany(toBeInserted);

	}

	public String findById(String artistId, String colName) {
		Document doc = connection.getDb().getCollection(colName).find(eq("_id", artistId)).first();

		return doc == null ? null : doc.getString("_id");
	}

	public ArtistList findAllArtists(String colName) {
		MongoCollection<Document> col = connection.getDb().getCollection(colName);

		Set<String> artists = new HashSet<String>();

		MongoCursor<Document> it = col.find().iterator();
		while (it.hasNext()) {

			Document a = it.next();
			artists.add(a.getString("_id"));
		}

		return new ArtistList(artists);

	}

}
