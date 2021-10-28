package ac.brunel.techdon.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBInterface {

    private static MongoClient client;
    private static MongoDatabase database;
    // TODO have multiple interfaces for different collections?
    private static MongoCollection<Document> collection;

    static {
        client = DBPreferences.getClient();
        database = client.getDatabase("techdon");
        collection = database.getCollection("users");
    }

}
