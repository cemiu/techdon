package ac.brunel.techdon.controller;

import ac.brunel.techdon.controller.util.DeviceHelper;
import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DevicePreference;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBStudent;
import ac.brunel.techdon.util.db.DBUser;
import org.apache.logging.log4j.util.Strings;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ac.brunel.techdon.controller.util.ResponseHelper.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

@RestController
public class StudentController {

    /**
     * endpoint to load device preferences for a student
     */
    @GetMapping("/api/student/devices/loadPreferences")
    public ResponseEntity<String> studentDevicesLoadPreferences(
            @RequestParam String authToken
    ) {
        DBStudent student = DBStudent.loadStudent(DBUser.Id.AUTH_TOKEN, authToken);
        if (student == null)
            return UNAUTHORIZED();

        ObjectId studentId = student.getId();
        List<String> deviceTypes = DevicePreference.getPreferredDevicesByStudent(studentId);

        return OK(deviceTypes.toString());
    }

    /**
     * endpoint to set preferences for which devices the students wants
     */
    @PostMapping("/api/student/devices/setPreferences")
    public ResponseEntity<String> studentDevicesSetPreferences(
            @RequestParam String authToken,
            @RequestParam(name = "deviceTypes") List<String> updatedDevices
    ) {
        DBStudent student = DBStudent.loadStudent(DBUser.Id.AUTH_TOKEN, authToken);
        if (student == null)
            return UNAUTHORIZED();

        if (updatedDevices.stream().anyMatch(type -> DeviceType.typeFromStringSafe(type) == null))
            return BAD_REQUEST("Invalid device type");

        ObjectId studentId = student.getId();
        List<String> currentDevices = DevicePreference.getPreferredDevicesByStudent(studentId);
        List<String> newDevices = new ArrayList(updatedDevices), removedDevices = new ArrayList<>(currentDevices);
        newDevices.removeAll(currentDevices);
        removedDevices.removeAll(newDevices);

        newDevices.forEach(type -> new DevicePreference(studentId, DeviceType.typeFromString(type), true));
        removedDevices.forEach(type -> new DevicePreference(studentId, DeviceType.typeFromString(type), false).removePreference());

        return OK();
    }

    /**
     * endpoint to set to all devices offered to a student
     */
    @GetMapping (value = "/api/student/devices/offeredDevices")
    public ResponseEntity<String> studentDevicesOfferedDevices(
            @RequestParam String authToken
    ) {
        DBStudent student = DBStudent.loadStudent(DBUser.Id.AUTH_TOKEN, authToken);
        if (student == null)
            return UNAUTHORIZED();

        ObjectId studentId = student.getId();
        List<String> deviceIds = Device.getDevicesByStudent(studentId, Optional.of(false));

        return OK(deviceIds.toString());
    }

    /**
     * endpoint to set to all Load student Devices that is offered
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping ("/api/student/devices/load")
    public ResponseEntity<String> studentDevicesLoad() {
        return null;
    }

    /**
     * endpoint to set to allow a student to claim
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping("/api/student/devices/claim")
    public ResponseEntity<String> studentDevicesClaim(
            @RequestParam @NotEmpty String authToken,
            @RequestParam @NotEmpty String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, false);
        if (device == null)
            return UNAUTHORIZED();

        device.setClaimed();
        // TODO why does the interface not accept object ids?
        //  does this even work?
        DBDonor donor = new DBDonor(DBUser.Id.USER_ID, device.getDonorId().toString());

        // TODO let the donor limit which of these options
        //  are seen, if time permits (at least first name + email)
        String donName = donor.getString(FIRST_NAME) + " " + donor.getString(LAST_NAME);
        String donEmail = donor.getString(EMAIL);
        String donPhone = donor.getString(PHONE);
        Object donAddress = donor.get(ADDRESS);

        // TODO trigger dispense of email (to donor and student)

        Document donorData = new Document("deviceId", deviceId)
                .append("donorName", donName)
                .append("donorEmail", donEmail)
                .append("donorPhone", donPhone)
                .append("donorAddress", donAddress);

        return OK(donorData.toJson());
    }

    /**
     * endpoint to decline an offered device
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping("/api/student/devices/decline")
    public ResponseEntity<String> studentDevicesDecline() {
        return null;
    }

}
    

