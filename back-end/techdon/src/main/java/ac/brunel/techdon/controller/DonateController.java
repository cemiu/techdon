package ac.brunel.techdon.controller;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBUser;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

@RestController
public class DonateController {

    /**
     * endpoint to donate a new device
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping("/api/donate/device/new")
    public String donateDeviceNew() {
        return "services";
    }

    /**
     * endpoint to show listed Devices
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping (value = "/api/donate/device/listedDevices", method = RequestMethod.GET,
            produces="text/json")
    public String donateDeviceListedDevices(
            @RequestParam String authToken
    ) {
        // TODO temp solution with DBDonor, while user object are still in development
        //  update to interact with account / donor object once implemented

        // throws an exception if the token is not valid
        DBDonor donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken);

        ObjectId donorId = donor.getObjectId(ID);
        ArrayList<String> deviceIds = Device.getDevicesByDonor(donorId);

        // returns devices as json array
        return deviceIds.toString();
    }

    /**
     * endpoint to load all the devices uploaded by the user
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping ("/api/donate/device/load")
    public String donateDeviceLoad() {
        return null;
    }

    /**
     * endpoint to remove a device which has been listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping (value = "/api/donate/device/remove", method = RequestMethod.DELETE)
    public String donateDeviceRemove(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ){
        // TODO temp solution with DBDonor, while user object are still in development
        //  update to interact with account / donor object once implemented

        // throws NoSuchElementException if auth token isn't valid for donor user
        DBDonor donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken);
        ObjectId donorId = donor.getObjectId(ID);

        // throws NoSuchElementException if no device with specified id
        //  exists and AccessDeniedException if the device doesn't belong to the donor
        Device device = new Device(deviceId, donorId, true);
        device.deleteDevice();

        return "success";
    }

    /**
     * endpoint to change values of a device listed for donation
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping (value = "/api/donate/device/update", method = RequestMethod.POST)
    public String donatedDeviceUpdate(
            @RequestParam(value = "authToken") String authToken,
            @RequestParam(value = "deviceId") String deviceId,
            @RequestParam(value = "deviceType", required = false) String deviceType,
            @RequestParam(value = "deviceName", required = false) String deviceName,
            @RequestParam(value = "deviceDescription", required = false) String deviceDescription
    ) {
        // TODO temp solution with DBDonor, while user object are still in development
        //  update to interact with account / donor object once implemented

        System.out.println("\nNext Request:");
        System.out.println(authToken);
        System.out.println(deviceId);
        System.out.println(deviceType);
        System.out.println(deviceName);
        System.out.println(deviceDescription);

        // throws NoSuchElementException if auth token isn't valid for any donor user
        DBDonor donor = new DBDonor(DBUser.Id.AUTH_TOKEN, authToken);
        ObjectId donorId = donor.getObjectId(ID);

        // throws NoSuchElementException if no device with specified id
        //  exists and AccessDeniedException if the device doesn't belong to the donor
        Device device = new Device(deviceId, donorId, true);

        if (deviceType != null) {
            // throws an IllegalArgumentException if device type is unknown
            DeviceType typeEnum = DeviceType.typeFromString(deviceType);
            device.setType(typeEnum);
        }

        if (deviceName != null) {
            device.setName(deviceName);
        }

        // TODO check if empty string is a possible input
        if (deviceDescription != null) {
            if (deviceDescription.equals(""))
                device.removeDescription();
            else
               device.setDescription(deviceDescription);
        }

        return "success";
    }

    /**
     * end point to update the information of a donated device
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/donate/device/load")
    public String donateDeviceUpdate(){
        return null;
    }
    
}
