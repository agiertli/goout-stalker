package org.goout.stalker.service.db;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.bson.Document;
import org.goout.stalker.config.GlobalConfig;
import org.goout.stalker.model.ArtistList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

@Stateless
public class DBService {

	private Long deleteCount;

	@EJB
	private DBConnection connection;

	@Inject
	private GlobalConfig config;

	Logger logger = LoggerFactory.getLogger(DBService.class.getName());

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

	public long getNotificationIdCount() {

		return connection.getDb().getCollection(config.NOTIFICATION_COL_NAME()).count();
	}

	public long removeNotificationId() {
		MongoCollection<Document> col = connection.getDb().getCollection(config.NOTIFICATION_COL_NAME());
		Document deleteMe = col.find().first();
		if (deleteMe != null) {
			col.deleteOne(eq("_id", deleteMe.getString("_id")));
			return 1;
		}
		return 0;
	}

	public void insertNotificationId(String notificationId) {

		MongoCollection<Document> col = connection.getDb().getCollection(config.NOTIFICATION_COL_NAME());
		Document doc = new Document().append("_id", notificationId);
		col.insertOne(doc);

	}

	public String findById(String documentId, String colName) {
		Document doc = connection.getDb().getCollection(colName).find(eq("_id", documentId)).first();

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
