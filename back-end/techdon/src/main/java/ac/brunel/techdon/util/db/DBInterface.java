package ac.brunel.techdon.util.db;

import static com.mongodb.client.model.Filters.*;

import ac.brunel.techdon.util.db.support.DBPreferences;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

class DBInterface {

    private static MongoClient client;
    private static MongoDatabase database;

    String collectionName;
    public MongoCollection<Document> collection; // todo set private later

    public DBInterface(String collection) {
        this.collectionName = collection;
        this.collection = database.getCollection(collection);
    }

    public Document getDocumentByField(String field, String value) {
        if (field == null || value == null || field.equals("") || value.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in "+
                    collectionName + " with value " + value + " in filed " + field);
        return collection.find(eq(field, value.toLowerCase())).first();
    }

    public boolean documentExists(String field, String value) {
        return getDocumentByField(field, value) != null;
    }

    static {
        client = DBPreferences.getClient();
        database = client.getDatabase("techdon");
    }

}
