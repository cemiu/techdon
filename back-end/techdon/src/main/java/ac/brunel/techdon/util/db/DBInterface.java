package ac.brunel.techdon.util.db;

import static com.mongodb.client.model.Filters.*;

import ac.brunel.techdon.util.db.fields.DBField;
import ac.brunel.techdon.util.db.support.DBPreferences;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.Map;

public class DBInterface {

    private static final MongoClient client;
    private static final MongoDatabase database;

    private final String collectionName;
    private final MongoCollection<Document> collection;

    /**
     * Initializes a new interface for
     * specified collection
     */
    public DBInterface(String collection) {
        this.collectionName = collection;
        this.collection = database.getCollection(collection);
    }

    /**
     * Uses a preferably unique field, to find a document in the collection
     */
    public Document getDocumentByField(String field, String value) {
        if (field == null || value == null || field.equals("") || value.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in " +
                    collectionName + " with value " + value + " in field " + field);
        return collection.find(eq(field, value)).first();
    }

    public Document getDocumentByField(String field, ObjectId id) {
        if (field == null || id == null || field.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in " +
                    collectionName + " with value " + id + " in field " + field);
        return collection.find(eq(field, id)).first();
    }

    /**
     * Uses a map of fields and their values to find a unique document
     */
    public Document getDocumentByFields(Map<String, Object> fields) {
        if (fields == null || fields.isEmpty() || fields.containsValue(null)
                || fields.containsValue(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in "
                    + collectionName + " with fields " + fields);
        // constructs a filter that concatenates all the fields
        Bson[] eqs = new Bson[fields.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            eqs[i] = eq(entry.getKey(), entry.getValue());
            i++;
        }
        return collection.find(and(eqs)).first();
    }

    private FindIterable<Document> getDocumentsByField(String field, ObjectId id) {
        if (field == null || id == null || field.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in " +
                    collectionName + " with value " + id + " in field " + field);
        return collection.find(eq(field, id));
    }

    public FindIterable<Document> getDocumentsByField(DBField field, ObjectId id) {
        return getDocumentsByField(field.getKey(), id);
    }

    /**
     * Updates a document in the collection by matching _id
     */
    public void update(Document doc) {
        update(doc.getObjectId("_id"), doc);
    }

    private void update(ObjectId id, Document doc) {
        collection.findOneAndReplace(eq("_id", id), doc);
    }

    /**
     * Takes in a remote document and uses its _id
     * field to find and delete a document from the remote
     * collection
     */
    public void delete(Document doc) {
        delete(doc.getObjectId("_id"));
    }

    public void delete(ObjectId id) {
        collection.findOneAndDelete(eq("_id", id)); // TODO does matching work with string id ?
    }

    /**
     * Pushes a new document to an object array
     * TODO throw error when object id is unknown
     */
    public void push(ObjectId id, DBField field, Document doc) {
        query("push", id, field.getKey(), doc);
    }

    /**
     * Method for constructing & sending basic DB queries
     * action: set (set field), inc (increment number), push (append to list)
     * id: _id of user being updated
     * field: name of field being updated
     * value: value field is being set to / incremented by / appended
     */
    private void query(String action, ObjectId id, String field, Object value) {
        Document query = new Document("$"+action , new Document(field, value));
        collection.updateOne(eq("_id", id), query);
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

    /**
     * Empty method without return type used
     * to initialize the connection to the database
     * upon the launch of the app
     */
    public static void init() {}

    static {
        client = DBPreferences.getClient();
        database = client.getDatabase("techdon");
    }

}
