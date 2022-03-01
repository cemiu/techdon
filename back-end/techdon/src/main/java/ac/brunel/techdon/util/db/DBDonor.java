package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.fields.DBDonorField;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.NoSuchElementException;

public class DBDonor extends DBUser {

    /**
     * Used to create new donor objects. After instantiating,
     * fill all required fields using {@link #set(DBDonorField, Object)}
     * and {@link #set(ac.brunel.techdon.util.db.fields.DBUserField, Object)},
     * then call the {@link #write()} method, to push new object to database.
     * After the initial write, the object has to be discarded, as further
     * modification attempts will not be possible!
     */
    public DBDonor() {
        super();
        set(USER_ROLE, "donor");
    }

    /**
     * Loads a remote donor object from the database, by using
     * their objectId, email, or auth token for identification
     * @throws NoSuchElementException when no donor with matching
     * criteria can be found (i.e. user is not a student, or does not exist)
     */
    public DBDonor(Id idMode, String id) {
        super(idMode, id);
        if (doesExistInDB() && getString(USER_ROLE).equals("donor"))
            return;

        // user doesn't exist, or isn't a donor
        throw new NoSuchElementException("Authentication token is invalid for user class: donor");
    }

    /**
     * Sets a field specified in {@link DBDonorField}
     * to a certain value
     */
    public void set(DBDonorField field, Object value) {
        set(field.getKey(), value);
    }

    /**
     * Returns List containing object ids of
     * all devices donated by DBDonor instance
     */
    public List<ObjectId> getDeviceList() {
        return Device.getDeviceIdsByDonor(getId());
    }

    /**
     * Takes in a device's object id, and returns whether
     * it is owned by the currently instantiated donor
     */
    public boolean ownsDevice(ObjectId deviceId) {
        List<ObjectId> deviceIds = getDeviceList();
        if (deviceId == null || deviceIds == null)
            return false;

        for (ObjectId id : deviceIds)
            if (deviceId.equals(id))
                return true;

        return false;
    }

    public static DBDonor loadDonor(Id idMode, String id) {
        try {
            return new DBDonor(idMode, id);
        } catch (Exception e) {
            return null; // no donor with matching criteria
        }
    }

}
