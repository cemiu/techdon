package ac.brunel.techdon.util.db;

import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.db.fields.DBDeviceField;
import ac.brunel.techdon.util.db.fields.DBDevicePrefField;
import ac.brunel.techdon.util.db.support.DBInstance;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ac.brunel.techdon.util.db.fields.DBDevicePrefField.*;

public class DBDevicePref implements DBInstance {

    private static final DBInterface db = new DBInterface("device_preferences");

    private DBWriteMode writeMode = DBWriteMode.AUTOMATIC;
    private Document doc;
    private boolean existsInDB = true;

    /**
     * Creates a new empty {@link DBDevicePref} object
     * for manual initialization
     */
    public DBDevicePref() {
        writeMode = DBWriteMode.MANUAL;
        existsInDB = false;
        doc = new Document();
        doc.put("_id", new ObjectId());
    }

    /**
     * Loads a preference from the database. Call {@link #doesExistInDB()}
     * after loading to make sure that the device loaded correctly
     */
    public DBDevicePref(ObjectId prefId) {
        this.doc = db.getDocumentByField("_id", prefId);
        if (this.doc == null)
            existsInDB = false;
    }

    /**
     * Loads a preference by student id and device type.
     * Call {@link #doesExistInDB()} after loading to make
     * sure that the device loaded correctly
     */
    public DBDevicePref(ObjectId studentId, DeviceType deviceType) {
        HashMap<String, Object> query = new HashMap<>();
        query.put(PREF_STUDENT_ID.getKey(), studentId);
        query.put(PREF_TYPE.getKey(), deviceType.toString());
        this.doc = db.getDocumentByFields(query);
        if (this.doc == null)
            existsInDB = false;
    }

    /**
     * Initializes the preference with an already loaded document
     */
    private DBDevicePref(Document doc) {
        this.doc = doc;
        if (this.doc == null)
            existsInDB = false;
    }

    /**
     * Returns a value of the db object, given a field
     */
    public Object get(String key) {
        return doc.get(key);
    }

    /**
     * Sets a field in the database, for internal use only
     * Use {@link #set(DBDevicePrefField, Object)} for external, safe use
     */
    private void set(String field, Object value) {
        doc.put(field, value);

        if (writeMode == DBWriteMode.AUTOMATIC)
            write();
    }

    /**
     * Sets a field defined in {@link DBDeviceField} to {@param value}
     */
    public void set(DBDevicePrefField field, Object value) {
        // _id is read only
        if (field == PREF_ID)
            throw new IllegalArgumentException("Cannot set the _id value for a preference. " +
                    "It is generated automatically and immutable.");

        set(field.getKey(), value);
    }

    /**
     * Returns whether the current {@link DBDevicePref} instance is
     * in the database. False when (1) the object was just created and
     * hasn't been written to the db yet, or (2) a preference
     * failed to load from the database
     */
    public boolean doesExistInDB() {
        return existsInDB;
    }

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
     * Changes the write mode, if the new write mode is automatic,
     * it writes all unwritten changes to the database
     */
    public void setWriteMode(DBWriteMode newMode) {
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
     * Returns the preference document for the next in line
     * for the specified device type
     */
    public static DBDevicePref getNextInQueueForDevice(DeviceType deviceType) {
        Document minDoc = db.getDocumentByMinField(PREF_DATE.getKey(), PREF_TYPE.getKey(), deviceType.toString());
        if (minDoc == null || minDoc.getObjectId(PREF_STUDENT_ID.getKey()) == null)
            return null;
        return new DBDevicePref(minDoc);
    }

    /**
     * Gets all files which a student set a preference for
     */
    public static List<String> getPreferredDevicesByStudent(ObjectId studentId) {
        FindIterable<Document> iterable = db.getDocumentsByField(PREF_STUDENT_ID, studentId);
        List<String> list = new ArrayList<>();

        // transforms iterable of preferences into list of device types
        iterable.map(doc -> doc.getString(PREF_TYPE.getKey()))
                .forEach(list::add);

        return list;
    }

}
