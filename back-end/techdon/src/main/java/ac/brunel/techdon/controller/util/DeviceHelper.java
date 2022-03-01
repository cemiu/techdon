package ac.brunel.techdon.controller.util;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.DBUser;

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
            device = new Device(deviceId, user.getId(), isDonor); // invalid device
        } catch (RuntimeException e) {
            return null;
        }

        return device;
    }

}
