package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import ac.brunel.techdon.util.db.fields.DBUserField;
import ac.brunel.techdon.util.db.support.DBInstance;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.bson.Document;
import org.bson.types.ObjectId;

public abstract class DBUser implements DBInstance {

    public static final DBInterface db = new DBInterface("users");

    private DBWriteMode writeMode = DBWriteMode.MANUAL;
    public Document doc;
    private boolean existsInDB = true;

    /**
     * Method for creating new user.
     * To be called from DBStudent / DBDonor exclusively
     */
    protected DBUser() {
        this.existsInDB = false;

        this.doc = new Document();
        doc.put("_id", new ObjectId());
    }

    /**
     * Loads a user from the database. Call {@link #doesExistInDB()}
     * after loading to make sure that the user exists
     */
    public DBUser(Id mode, String id) {
        if (mode == Id.EMAIL)
            id = id.toLowerCase();

        this.doc = db.getDocumentByField(mode.key, id);
        if (this.doc == null)
            existsInDB = false;
    }

    protected ObjectId getId() {
        if (!existsInDB)
            return null;
        return (ObjectId) get(ID);
    }

    /**
     * Sets a field in the database, for internal use only
     * Use {@link #set(DBUserField, Object)} for external, safe use
     */
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

        if (writeMode == DBWriteMode.AUTOMATIC)
            write(); // TODO write the entire document, or just write differences since last write
                    // maybe keep track of changes in a separate document and flush after every write
                    // or construct bson update command as you go
    }

    /**
     * Sets a field defined in {@link DBUserField} to {@param value}
     */
    public void set(DBUserField field, Object value) {
        // _id is read only
        if (field == ID)
            throw new IllegalArgumentException("Cannot set the _id value for a user." +
                    "It is generated automatically and read only");

        set(field.getKey(), value);
    }

    /**
     * Resolves an object, given a field
     */
    public Object get(String key) {
        return doc.get(key);
    }

    /**
     * Returns whether the current {@link DBUser} instance is
     * in the database. False when (1) the user was just created or
     * (2) a user couldn't be found in the database
     */
    public boolean doesExistInDB() {
        return existsInDB;
    }

    /**
     * Manually writes the current user to the database
     */
    public void write() {
        if (!existsInDB) {
            db.insertNew(doc);
        } else
            db.update(doc);
    }

    /**
     * Returns the current write mode
     */
    public DBWriteMode getWriteMode() {
        return writeMode;
    }

    /**
     * Sets a new write mode, and writes if new
     * write mode is automatic
     */
    public void setWriteMode(DBWriteMode newMode) {
        // TODO only write if there is anything to write
        if (writeMode != DBWriteMode.AUTOMATIC && newMode == DBWriteMode.AUTOMATIC)
            write();
        writeMode = newMode;
    }

    /**
     * An enum that specifies which unique identifier
     * is used to load the user from the database
     * (object id, auth token, email)
     */
    public enum Id {
        USER_ID(ID.getKey()),
        AUTH_TOKEN(AUTH_TOKENS.getKey()),
        EMAIL(DBUserField.EMAIL.getKey());

        private final String key;

        Id(String keyName) {
            this.key = keyName;
        }

        private String getAuthField() {
            return key;
        }
    }

}
