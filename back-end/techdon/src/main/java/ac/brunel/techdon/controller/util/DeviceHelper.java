package ac.brunel.techdon.controller.util;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBUser;
import org.bson.types.ObjectId;

import static ac.brunel.techdon.util.db.fields.DBUserField.ID;

public class DeviceHelper {

    /**
     * Loads a device from an authToken and a deviceId.
     * Returns null if the device is not found.
     */
    public static Device getDeviceByAuth(String authToken, String deviceId, boolean isDonor) {
        DBUser user;
        Device device;
        try {
            user = new DBUser(DBUser.Id.AUTH_TOKEN, authToken); // invalid auth
            ObjectId donorId = user.getObjectId(ID);
            device = new Device(deviceId, donorId, isDonor); // invalid device
        } catch (RuntimeException e) {
            return null;
        }

        return device;
    }

}
