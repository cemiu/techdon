package ac.brunel.techdon.util.db;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.fields.DBDonorField;

public class DBDonor extends DBUser {

    // to be used for new account creation exclusively
    public DBDonor() {
        super();
        set(USER_ROLE, "donor");
    }

    // adds a new device to the database
    public void addDevice(Device device) {
        if (device.getDeviceId() == null)
            throw new IllegalArgumentException("Device ID has to be set before being added to DB"); // TODO does it?

        // TODO add new device
    }

    // removes a device from the database
    public boolean deleteDevice(Device device) {
        return deleteDevice(device.getDeviceId());
    }

    // updates device information in database
    public boolean updateDevice(Device device) {
        if (device.getDeviceId() == null)
            throw new IllegalArgumentException("Cannot update unknown device.");

        return false; // TODO
    }

    public boolean deleteDevice(String id) {
        if (id == null)
            throw new IllegalArgumentException("Cannot delete unknown device from DB");
        return false; // TODO attempt to delete device BELONGING TO CURRENT DONOR; return whether successful
    }

    public void set(DBDonorField field, Object value) {
        set(field.getKey(), value);
    }

}
