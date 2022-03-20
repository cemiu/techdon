package ac.brunel.techdon.controller;

import ac.brunel.techdon.controller.util.DeviceHelper;
import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBUser;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

import static ac.brunel.techdon.controller.util.ResponseHelper.*;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DonorController {

    private static final Pattern POST_CODE_PATTERN = Pattern.compile("^[A-Z]{1,2}[0-9R][0-9A-Z]? [0-9][ABD-HJLNP-UW-Z]{2}$");

    /**
     * endpoint to donate a new device
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping("/api/donor/device/new")
    public ResponseEntity<String> donorDeviceNew(
            @RequestParam String authToken,
            @RequestParam String deviceType,
            @RequestParam String deviceName,
            @RequestParam(required = false) String deviceLocation,
            @RequestParam(required = false) String deviceDescription
    ) {
        DBDonor donor = DBDonor.loadDonor(DBUser.Id.AUTH_TOKEN, authToken);
        DeviceType type = DeviceType.typeFromStringSafe(deviceType);
        if (donor == null || type == null || deviceName.isEmpty())
            return donor == null ? UNAUTHORIZED() : BAD_REQUEST();

        String parsedLocation = null, parsedDescription = null;
        if (deviceLocation != null) {
            if (!POST_CODE_PATTERN.matcher(deviceLocation).matches())
                return BAD_REQUEST("The postcode is invalid, try it in the format 'EC1A 1BB' instead.");
            parsedLocation = deviceLocation;
        }

        if (deviceDescription != null && !deviceDescription.isEmpty())
            parsedDescription = deviceDescription;

        Device device = new Device(donor.getId(), type, deviceName);
        if (parsedDescription != null)
            device.setDescription(parsedDescription);
        if (parsedLocation != null)
            device.setLocation(parsedLocation);

        return OK(device.toDoc().toJson());
    }

    /**
     * endpoint to show listed Devices
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping (value = "/api/donor/device/listedDevices")
    public ResponseEntity<String> donorListedDevices(
            @RequestParam String authToken
    ) {
        DBDonor donor = DBDonor.loadDonor(DBUser.Id.AUTH_TOKEN, authToken);
        if (donor == null)
            return UNAUTHORIZED();

        List<String> deviceIds = Device.getDevicesByDonor(donor.getId());
        return OK(new Document("devices", deviceIds).toJson());
    }

    /**
     * endpoint for a donor to load a specific device
     */
    @GetMapping("/api/donor/device/load")
    public ResponseEntity<String> donorDeviceLoad(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, true);
        if (device == null)
            return UNAUTHORIZED();

        return OK(device.toDoc().toJson());
    }

    /**
     * endpoint to remove a device which has been listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @DeleteMapping (value = "/api/donor/device/remove")
    public ResponseEntity<String> donorDeviceRemove(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, true);
        if (device == null)
            return UNAUTHORIZED();

        device.deleteDevice(); // deletes the device
        return OK_SIMPLE("Successfully deleted device");
    }

    /**
     * endpoint to change values of a device listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping (value = "/api/donor/device/update")
    public ResponseEntity<String> donorDeviceUpdate(
            @RequestParam String authToken,
            @RequestParam String deviceId,
            @RequestParam(required = false) String deviceType,
            @RequestParam(required = false) String deviceName,
            @RequestParam(required = false) String deviceLocation,
            @RequestParam(required = false) String deviceDescription
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, true);
        if (device == null)
            return UNAUTHORIZED();

        // Validate inputs first
        DeviceType safeType = null;
        String safeName = null, safeLocation = null, safeDescription = null;
        if (deviceType != null && !deviceType.isEmpty()) {
            safeType = DeviceType.typeFromStringSafe(deviceType);
            if (safeType == null)
                return BAD_REQUEST("Invalid device type");
        }

        if (deviceName != null && !deviceName.isEmpty()) {
            if (deviceName.length() > 300)
                return BAD_REQUEST("The name exceeds the upper character limit (300)");
            safeName = deviceName;
        }

        if (deviceLocation != null) {
            if (!deviceLocation.equals("") && !POST_CODE_PATTERN.matcher(deviceLocation).matches())
                return BAD_REQUEST("The postcode \"" + deviceLocation + "\" is invalid, try it in the format \"EC1A 1BB\" instead.");
            safeLocation = deviceLocation;
        }

        if (deviceDescription != null) {
            if (deviceDescription.length() > 2000)
                return BAD_REQUEST("The description exceeds the upper character limit (2000)");
            safeDescription = deviceDescription;
        }

        // Set the validated parameters
        if (safeType != null)
            device.setType(safeType);
        if (safeName != null)
            device.setName(deviceName);
        if (safeDescription != null)
            if (safeDescription.equals(""))
                device.removeDescription();
            else
                device.setDescription(safeDescription);
        if (safeLocation != null)
            if (safeLocation.equals(""))
                device.removeLocation();
            else
                device.setLocation(safeLocation);

        return OK();
    }
    
}
