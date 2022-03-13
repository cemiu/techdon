package ac.brunel.techdon.util.db;

import static com.mongodb.client.model.Filters.*;

import ac.brunel.techdon.util.db.fields.DBField;
import ac.brunel.techdon.util.db.support.DBPreferences;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonType;
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
        return getDocumentsByFields(fields).first();
    }

    public FindIterable<Document> getDocumentsByFields(Map<String, Object> fields) {
        if (fields == null || fields.isEmpty() || fields.containsValue(null)
                || fields.containsValue(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in "
                    + collectionName + " with fields " + fields);
        // constructs a filter that concatenates all the fields
        Bson[] eqs = new Bson[fields.size()];
        int i = 0;
        for (Map.Entry<String, Object> entry : fields.entrySet()) {
            eqs[i] = eq(entry.getKey(), entry.getValue() == BsonType.NULL ? null : entry.getValue());
            i++;
        }
        return collection.find(and(eqs));
    }

    /**
     * Returns the document with the smallest value for the minimizing field
     */
    public Document getDocumentByMinField(String minimizingField, String selectingField, Object value) {
        if (minimizingField == null || selectingField == null || value == null || minimizingField.equals("") || selectingField.equals(""))
            throw new IllegalArgumentException("Cannot lookup DB Document in " +
                    collectionName + " with value " + value + " in field " + minimizingField);

        return collection.find(eq(selectingField, value)).sort(new Document(minimizingField, 1)).first();
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
        collection.findOneAndDelete(eq("_id", id));
    }

    /**
     * Inserts a new document without _id into the collection
     */
    public void insertNew(Document doc) {
        collection.insertOne(doc);
    }

    static {
        client = DBPreferences.getClient();
        database = client.getDatabase("techdon");
    }

}
