package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import static ac.brunel.techdon.util.db.fields.DBDonorField.DeviceField.*;
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
     * Takes in a new device and adds it to the instanced
     * donor's list of donated devices
     */
    public void addDevice(Device device) {
        if (device.getDeviceId() == null)
            throw new IllegalArgumentException("Device ID has to be set before being added to DB"); // TODO does it?

        if (!doesExistInDB())
            throw new IllegalArgumentException("Cannot add device to user before they have been written to the DB");
        // TODO take write mode into consideration (add device to query queue, and execute entire queue upon write())

        db.push(getId(), DONATED_DEVICES, device.toDocument());
    }

    /**
     * Takes in a device belonging to the instanced
     * donor, and updates its values in the database.
     * Returns whether operation was successful
     */
    public boolean updateDevice(Device device) {
        if (device == null || device.getDeviceId() == null)
            return false;

        return true; // TODO
    }

    /**
     * Takes in an object id of a device belonging to the instanced
     * donor and attempts to delete the device.
     * Returns whether the deletion was successful
     */
    public boolean deleteDevice(ObjectId deviceId) {
        if (deviceId == null)
            return false;

        return true; // TODO attempt to delete device BELONGING TO CURRENT DONOR; return whether successful
    }

    /**
     * Removes a device belonging to the user from the database
     * Only the devices' IDs have to match
     */
    public boolean deleteDevice(Device device) {
        if (device == null)
            return false;

        return deleteDevice(device.getDeviceId());
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

        Object deviceListRaw = this.get(DONATED_DEVICES);
        ArrayList<ObjectId> listOut = new ArrayList<>();
        if (deviceListRaw == null)
            return listOut;

        ArrayList<Document> deviceList = (ArrayList<Document>) deviceListRaw;
        deviceList.forEach(x -> listOut.add((ObjectId) x.get(DEVICE_ID.getKey())));
        return listOut;
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
