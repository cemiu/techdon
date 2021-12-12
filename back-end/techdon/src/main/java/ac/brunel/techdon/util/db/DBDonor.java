package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.fields.DBDonorField;
import ac.brunel.techdon.util.db.fields.DBStudentField;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        // user couldn't be loaded
        throw new NoSuchElementException("Could not load user with " + idMode + ": " + id + ", as a donor");
    }

    /**
     * Sets a field specified in {@link DBDonorField}
     * to a certain value
     */
    public void set(DBDonorField field, Object value) {
        set(field.getKey(), value);
    }

    /**
     * Returns ArrayList containing object ids of
     * all devices donated by DBDonor instance
     */
    public ArrayList<ObjectId> getDeviceList() {
        if (doc == null)
            return null;

        // TODO ... query devices collection by donor foreign key & compile resulting
        // ids into arraylist

        return null;
    }

    /**
     * Takes in a device's object id, and returns whether
     * it is owned by the currently instantiated donor
     */
    public boolean ownsDevice(ObjectId deviceId) {
        ArrayList<ObjectId> deviceIds = getDeviceList();
        if (deviceId == null || deviceIds == null)
            return false;

        for (ObjectId id : deviceIds)
            if (deviceId.equals(id))
                return true;

        return false;
    }

    /**
     * Takes in a device and returns whether it is owned by
     * the current user. Note: the device does not have to be
     * a complete match, just the IDs have to match
     */
    public boolean ownsDevice(Device device) {
        if (device == null)
            return false;

        return ownsDevice(device.getDeviceId());
    }

}
