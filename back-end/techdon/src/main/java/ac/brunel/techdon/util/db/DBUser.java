package ac.brunel.techdon.util.db;

import ac.brunel.techdon.util.db.support.DBInstance;
import ac.brunel.techdon.util.db.fields.DBUserField;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.bson.Document;

public abstract class DBUser implements DBInstance {

    private static final DBInterface db = new DBInterface("users");

    private DBWriteMode writeMode;
    private Document doc;
    private boolean existsInDB = true;

    protected DBUser() {
        this.existsInDB = false;
        this.doc = new Document();
        // TODO (no default automatic mode, set to automatic after all fields are instantiated)
    }

    public DBUser(ID mode, String id) {
        this(mode, id, DBWriteMode.MANUAL);
    }

    public DBUser(ID mode, String id, DBWriteMode writeMode) {
        this.writeMode = writeMode;
        this.doc = db.getDocumentByField(mode.key, id);
    }

    protected void set(String field, Object value) {
        String[] path = field.split("/");

        if (path.length == 1)
            doc.put(field, value);
        else if (path.length == 2) {
            Document subDoc = (Document) doc.get(path[0]);
            if (subDoc == null)
                subDoc = new Document();
            subDoc.put(path[1], value);
        } else
            throw new IllegalArgumentException("Path " + field + " contains two sub-objects." +
                    "Try limiting yourself to one object; if you can't, talk to Philipp to expand functionality.");

        if (writeMode == DBWriteMode.AUTOMATIC);
            write(); // TODO write the entire document, or just write differences since last write
                    // maybe keep track of changes in a separate document and flush after every write
                    // or construct bson update command as you go
    }

    public void set(DBUserField field, Object value) {
        // _id is read only
        if (field == DBUserField.ID)
            throw new IllegalArgumentException("Cannot set the _id value for a user." +
                    "It is generated automatically and read only");
        // makes sure that type matches
        /**if(!((Class) field.getType()).isInstance(value))
            throw new IllegalArgumentException("Cannot set " + value.getClass() + " as value for field "
                    + field.getKey() + ", expected " + field.getType());*/ // TODO do we need to specify and check type on every interaction ?
        set(field.getKey(), value);
    }

    public Object get(String key) {
        return doc.get(key);
    }

    public DBWriteMode getWriteMode() {
        return writeMode;
    }

    public void setWriteMode(DBWriteMode newMode) {
        if (writeMode != DBWriteMode.AUTOMATIC && newMode == DBWriteMode.AUTOMATIC)
            write();
        writeMode = newMode;
    }

    public void write() {
        if (!existsInDB)
            db.collection.insertOne(doc); // TODO load id back in from database, also change this
        else {
            // TODO plz fix, very bad, no filters outside of dbinterface class
            db.collection.findOneAndReplace(com.mongodb.client.model.Filters.eq("_id", doc.get(DBUserField.ID)), doc);
        }
    }

    public static enum ID {
        USER_ID("_id"),
        AUTH_TOKEN("auth_token"),
        EMAIL("email");

        private String key;

        ID(String keyName) {
            this.key = keyName;
        }

        private String getAuthField() {
            return key;
        }
    }

}
