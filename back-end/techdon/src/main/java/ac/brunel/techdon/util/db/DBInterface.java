package ac.brunel.techdon.util.db;

import static com.mongodb.client.model.Filters.*;

import ac.brunel.techdon.util.db.fields.DBDonorField;
import ac.brunel.techdon.util.db.fields.DBField;
import ac.brunel.techdon.util.db.support.DBPreferences;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;

class DBInterface {

    // TODO REMOVE THIS BEFORE MERGING TO MASTER BRANCH
    public static void main(String[] args) {
        DBInterface i = new DBInterface("users");
        // resolves user by device id
        // is it possible to just get a single element of the device array back ?
        // or is it necessary to iterate over the entire array to find specific device
        // .. maybe devices should be stored in a separate list, with their owning user being a foreign key ?
        Document a = i.collection.find(eq("donated_devices.device_id", new ObjectId("61ab77b40be1bf0adf193d9a"))).first();
        //System.out.println(a.get("donated_devices"));
        //ArrayList<Document> b = (ArrayList<Document>) a.get("donated_devices");
        //for (Document d: b) {
        //    System.out.println(d.toJson());
        //}
    }

    private static MongoClient client;
    private static MongoDatabase database;

    String collectionName;
    public MongoCollection<Document> collection;

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
     * Updates a document in the collection by matching _id
     */
    public void update(Document doc) {
        update(doc.getString("_id"), doc);
    }

    private void update(String id, Document doc) {
        collection.findOneAndReplace(eq("_id", id), doc);
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
        //System.out.println(query.toJson());
        //System.out.println(id);
        //System.out.println(collection.find(eq("_id", id)).first().toJson());
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
