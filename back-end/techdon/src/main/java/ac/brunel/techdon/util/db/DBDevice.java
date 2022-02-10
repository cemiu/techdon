package ac.brunel.techdon.util.db;

import ac.brunel.techdon.util.db.fields.DBDeviceField;
import ac.brunel.techdon.util.db.fields.DBField;
import ac.brunel.techdon.util.db.support.DBInstance;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static ac.brunel.techdon.util.db.fields.DBDeviceField.*;

public class DBDevice  implements DBInstance {

    private static final DBInterface db = new DBInterface("devices");

    private DBWriteMode writeMode = DBWriteMode.MANUAL;
    public Document doc;
    private boolean existsInDB = true;

    public DBDevice() {
        existsInDB = false;
        doc = new Document();
        doc.put("_id", new ObjectId());
    }

    public DBDevice(String deviceId) {
        this(new ObjectId(deviceId));
    }

    /**
     * Loads a device from the database. Call {@link #doesExistInDB()}
     * after loading to make sure that the device loaded correctly
     */
    public DBDevice(ObjectId deviceId) {
        this.doc = db.getDocumentByField("_id", deviceId);
        if (this.doc == null)
            existsInDB = false;
    }

    /**
     * Resolves an object, given a field
     */
    public Object get(String key) {
        return doc.get(key);
    }

    /**
     * Sets a field in the database, for internal use only
     * Use {@link #set(DBDeviceField, Object)} for external, safe use
     */
    protected void set(String field, Object value) {
        doc.put(field, value);

        if (writeMode == DBWriteMode.AUTOMATIC)
            write();
    }

    /**
     * Sets a field defined in {@link DBDeviceField} to {@param value}
     */
    public void set(DBDeviceField field, Object value) {
        // _id is read only
        if (field == DEVICE_ID)
            throw new IllegalArgumentException("Cannot set the _id value for a user." +
                    "It is generated automatically and read only");

        set(field.getKey(), value);
    }

    /**
     * Removes the specified field from the remote object
     */
    public void remove(DBDeviceField field) {
        doc.remove(field.getKey());

        // TODO update single value instead of entire document
        if (writeMode == DBWriteMode.AUTOMATIC)
            write();
    }

    /**
     * Returns whether the current {@link DBUser} instance is
     * in the database. False when (1) the user was just created or
     * (2) a user couldn't be found in the database
     */
    public boolean doesExistInDB() {
        return existsInDB;
    }

    public void write() {
        if (!existsInDB) {
            db.insertNew(doc);
            existsInDB = true;
        } else
            db.update(doc); // TODO make more sophisticated, with update queue
    }

    /**
     * Returns the current write mode
     */
    public DBWriteMode getWriteMode() {
        return writeMode;
    }

    /**
     * Changes the write mode, if the new write mode is automatic,
     * it writes all unwritten changes to the database
     */
    public void setWriteMode(DBWriteMode newMode) {
        // TODO only write, if there is anything to write (once write queue has
        //  been implemented)
        if (writeMode != DBWriteMode.AUTOMATIC && newMode == DBWriteMode.AUTOMATIC)
            write();
        writeMode = newMode;
    }

    /**
     * Deletes the device from the database
     */
    public void delete() {
        if (!existsInDB)
            throw new IllegalArgumentException("Cannot delete a remote device object that is not in the database.");
        db.delete(doc);
        doc = null;
    }

    /**
     * Gets all devices associated with specified
     * donor / student and returns a list containing
     * their IDs
     */
    public static List<ObjectId> getDevicesByUser(ObjectId userId, boolean isDonor) {
        DBField field = isDonor ? DEVICE_DONOR : DEVICE_ASSIGNED_STUDENT;
        FindIterable<Document> iterable = db.getDocumentsByField( field, userId);
        List<ObjectId> list = new ArrayList<>();

        // transforms iterable of device docs into list of device ids
        iterable.map(doc -> doc.getObjectId(DEVICE_ID.getKey()))
                .forEach(list::add);

        return list;
    }

}
