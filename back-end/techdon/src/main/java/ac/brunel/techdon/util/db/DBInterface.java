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
    private MongoCollection<Document> collection;

    public DBInterface(String collection) {
        this.collectionName = collection;
        this.collection = database.getCollection(collection);
    }

    /**
     * Uses a preferably unique field, to find a document in the collection
     */
    public Document getDocumentByField(String field, String value) {
        if (field == null || value == null || field.equals("") || value.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in "+
                    collectionName + " with value " + value + " in filed " + field);
        return collection.find(eq(field, value)).first();
    }

    /**
     * Updates a document in the collection by matching _id
     */
    public void update(Document doc) {
        update(doc.getString("_id"), doc);
    }

    private void update(String id, Document doc) {
        collection.findOneAndReplace(eq("_id", id), doc);
    }

    /**
     * Inserts a new document without _id into the collection
     */
    public void insertNew(Document doc) {
        collection.insertOne(doc);
    }

    public boolean documentExists(String field, String value) {
        return getDocumentByField(field, value) != null;
    }

    static {
        client = DBPreferences.getClient();
        database = client.getDatabase("techdon");
    }

}
