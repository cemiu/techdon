package ac.brunel.techdon.controller;

import ac.brunel.techdon.controller.util.DeviceHelper;
import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DevicePreference;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.EmailHelper;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBStudent;
import ac.brunel.techdon.util.db.DBUser;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static ac.brunel.techdon.controller.util.ResponseHelper.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

@CrossOrigin(origins = "*", maxAge = 3600)
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

        return OK(new Document("deviceTypes", deviceTypes).toJson());
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
        List<String> newDevices = new ArrayList<>(updatedDevices), removedDevices = new ArrayList<>(currentDevices);
        newDevices.removeAll(currentDevices);
        removedDevices.removeAll(updatedDevices);

        newDevices.forEach(type -> new DevicePreference(studentId, DeviceType.typeFromString(type), true));
        removedDevices.forEach(type -> new DevicePreference(studentId, DeviceType.typeFromString(type), false).removePreference());

        return OK();
    }

    /**
     * endpoint to get to all devices offered to a student
     */
    @GetMapping (value = "/api/student/devices/offeredDevices")
    public ResponseEntity<String> studentDevicesOfferedDevices(
            @RequestParam String authToken
    ) {
        DBStudent student = DBStudent.loadStudent(DBUser.Id.AUTH_TOKEN, authToken);
        if (student == null)
            return UNAUTHORIZED();

        ObjectId studentId = student.getId();
        List<String> deviceIds = Device.getDevicesByStudent(studentId, false);

        return OK(new Document("devices", deviceIds).toJson());
    }

    /**
     * endpoint for a student to load a specific device offered to them
     */
    @GetMapping ("/api/student/devices/load")
    public ResponseEntity<String> studentDevicesLoad(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, false);
        if (device == null)
            return UNAUTHORIZED();

        return OK(device.toDoc().toJson());
    }

    /**
     * endpoint to set to allow a student to claim a device
     */
    @GetMapping("/api/student/devices/claim")
    public ResponseEntity<String> studentDevicesClaim(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, false);
        if (device == null)
            return UNAUTHORIZED();

        DBDonor donor = new DBDonor(DBUser.Id.USER_ID, device.getDonorId().toString());

        try {
            if (!device.isHasBeenClaimed()) {
                DBStudent student = DBStudent.loadStudent(DBUser.Id.USER_ID, device.getAssignedStudent().toString());
                SimpleMailMessage mailStudent = EmailHelper.getContactDetailsEmail(
                        student.getString(EMAIL),
                        student.getString(FIRST_NAME),
                        donor.getString(FIRST_NAME) + " " + donor.getString(LAST_NAME),
                        donor.getString(PHONE),
                        donor.getString(EMAIL),
                        false
                );

                SimpleMailMessage mailDonor = EmailHelper.getContactDetailsEmail(
                        donor.getString(EMAIL),
                        donor.getString(FIRST_NAME),
                        student.getString(FIRST_NAME) + " " + student.getString(LAST_NAME),
                        student.getString(PHONE),
                        student.getString(EMAIL),
                        true
                );

                EmailHelper.sendEmail(mailStudent);
                EmailHelper.sendEmail(mailDonor);
            }
        } catch (Exception e) {}

        device.setClaimed();

        Document donorData = device.toDoc()
                .append("donorName", donor.getString(FIRST_NAME) + " " + donor.getString(LAST_NAME))
                .append("donorEmail", donor.getString(EMAIL))
                .append("donorPhone", donor.getString(PHONE));

        return OK(donorData.toJson());
    }

    /**
     * endpoint to decline an offered device
     * check documentation for more info on inputs / outputs expected
     */
    @PostMapping("/api/student/devices/decline")
    public ResponseEntity<String> studentDevicesDecline(
            @RequestParam String authToken,
            @RequestParam String deviceId
    ) {
        Device device = DeviceHelper.getDeviceByAuth(authToken, deviceId, false);
        if (device == null)
            return UNAUTHORIZED();

        // sets the student to the back of the queue and remove the assignment
        DBStudent student = new DBStudent(DBUser.Id.AUTH_TOKEN, authToken);
        new DevicePreference(student.getId(), device.getType(), false).resetSelectionDate();
        device.unassign();

        return OK();
    }

}
