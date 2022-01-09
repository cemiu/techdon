package ac.brunel.techdon.controller;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBUser;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.NoSuchElementException;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.controller.util.ResponseHelper.*;

@RestController
public class DonorController {

    /**
     * endpoint to donate a new device
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping("/api/donor/device/new")
    public String donorDeviceNew() {
        return "services";
    }

    /**
     * endpoint to show listed Devices
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping (value = "/api/donor/device/listedDevices", produces="text/json")
    public ResponseEntity<String> donorListedDevices(
            @RequestParam @NotBlank String authToken
    ) {
        // TODO temp solution with DBDonor, while user object are still in development
        //  update to interact with account / donor object once implemented

        DBDonor donor;
        try {
            donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken);
        } catch (RuntimeException e) {
            return UNAUTHORIZED(); // invalid donor auth token
        }

        ObjectId donorId = donor.getObjectId(ID);
        List<String> deviceIds = Device.getDevicesByDonor(donorId);

        // returns devices as json array
        return OK(deviceIds.toString());
    }

    /**
     * end point to update the information of a donated device
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/donor/device/load")
    public String donorDeviceLoad() {
        return null;
    }

    /**
     * endpoint to remove a device which has been listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping (value = "/api/donor/device/remove")
    public ResponseEntity<String> donorDeviceRemove(
            @RequestParam @NotEmpty String authToken,
            @RequestParam @NotEmpty String deviceId
    ) {
        // TODO temp solution with DBDonor, while user object are still in development
        //  update to interact with account / donor object once implemented

        // throws NoSuchElementException if auth token isn't valid for donor user
        DBDonor donor;
        Device device;
        try {
            donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken); // invalid donor auth token
            ObjectId donorId = donor.getObjectId(ID);
            device = new Device(deviceId, donorId, true); // invalid device or user
        } catch (RuntimeException e) {
            return UNAUTHORIZED();
        }

        device.deleteDevice(); // deletes the device
        return OK("Successfully deleted device");
    }

    /**
     * endpoint to change values of a device listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping (value = "/api/donor/device/update", method = RequestMethod.POST)
    public ResponseEntity<String> donorDeviceUpdate(
            @RequestParam(value = "authToken") String authToken,
            @RequestParam(value = "deviceId") String deviceId,
            @RequestParam(value = "deviceType", required = false) String deviceType,
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @RequestParam(value = "deviceLocation", required = false) String deviceLocation,
            @RequestParam(value = "deviceDescription", required = false) String deviceDescription
    ) {
        /* TODO temp solution with DBDonor, while user object are still in development
        **  update to interact with account / donor object once implemented */

        DBDonor donor;
        Device device;
        try {
            donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken); // invalid auth
            ObjectId donorId = donor.getObjectId(ID);
            device = new Device(deviceId, donorId, true); // invalid device
        } catch (RuntimeException e) {
            return UNAUTHORIZED();
        }

        /* Validate the validity of all inputs prior to
        ** writing anything to the database */
        DeviceType safeType = null;
        String safeName = null, safeLocation = null, safeDescription = null;
        if (deviceType != null)
            try {
                safeType = DeviceType.typeFromString(deviceType); // unknown type
            } catch (IllegalArgumentException e) {
                return BAD_REQUEST("Unknown device type");
            }

        // TODO check for constraints (or specify in request)
        if (deviceName != null) {
            if (deviceName.length() > 300)
                return BAD_REQUEST("The name exceeds the upper character limit (300)");
            safeName = deviceName;
        }

        if (safeLocation != null) {
            // TODO validate city or postcode
            boolean isValid = true; // (temp, remove later)
            if (!isValid)
                return BAD_REQUEST("Location could not be resolved, try your postcode instead: 0AA 0AA");
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
            device.setLocation(safeLocation);

        return OK();
    }
    
}
