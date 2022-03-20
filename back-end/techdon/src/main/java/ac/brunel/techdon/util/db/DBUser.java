package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import ac.brunel.techdon.util.db.DBUser.Id;
import ac.brunel.techdon.util.db.fields.DBUserField;
import ac.brunel.techdon.util.db.support.DBInstance;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.bson.Document;
import org.bson.types.ObjectId;

public class DBUser implements DBInstance {

    private static final DBInterface db = new DBInterface("users");

    private DBWriteMode writeMode = DBWriteMode.AUTOMATIC;
    private Document doc;
    private boolean existsInDB = true;

    /**
     * Method for creating new user.
     * To be called from DBStudent / DBDonor exclusively
     */
    protected DBUser() {
        writeMode = DBWriteMode.MANUAL;
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

        if (mode == Id.USER_ID)
            this.doc = db.getDocumentByField(mode.getAuthField(), new ObjectId(id));
        else
            this.doc = db.getDocumentByField(mode.getAuthField(), id);
        if (this.doc == null)
            existsInDB = false;
    }

    public ObjectId getId() {
        if (!existsInDB)
            return null;
        return getObjectId(ID);
    }

    /**
     * Sets a field in the database, for internal use only
     * Use {@link #set(DBUserField, Object)} for external, safe use
     */
    public void set(String field, Object value) {
        doc.put(field, value);

        if (writeMode == DBWriteMode.AUTOMATIC)
            write();
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
            existsInDB = true;
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
        if (writeMode != DBWriteMode.AUTOMATIC && newMode == DBWriteMode.AUTOMATIC)
            write();
        writeMode = newMode;
    }

    /**
     * Deletes the user from the database
     */
    public void delete() {
        if (!existsInDB)
            throw new IllegalArgumentException("Cannot delete a remote device object that is not in the database.");
        db.delete(doc);
        doc = null;
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
    
    public static DBUser loadUser(Id idMode, String id) {
        try {
            DBUser user = new DBUser(idMode, id);
            if (user.doesExistInDB())
                return user;
            throw new IllegalArgumentException();
        } catch (Exception e) {
            return null; // no user with matching criteria
        }
    }

}
