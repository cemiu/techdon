package ac.brunel.techdon.controller;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBStudent;
import ac.brunel.techdon.util.db.DBUser;
import ac.brunel.techdon.util.db.fields.DBDonorField;
import ac.brunel.techdon.util.db.fields.DBUserField;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.NoSuchElementException;

import static ac.brunel.techdon.controller.util.ResponseHelper.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

@RestController
public class StudentController {

    /**
     * endpoint to register an account
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/student/devices/loadPreferences")
    public String studentDevicesLoadPreferences() {
        return null;
    }

    /**
     * endpoint to set preferences for which devices the students wants
     * check documentation for more info on inputs / outputs expected
     */
    @RequestMapping("/api/student/devices/setPreferences")
    public String studentDevicesSetPreferences() {
        return null;
    }

    /**
     * endpoint to set to all devices offered to a student
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping (value = "/api/student/devices/offeredDevices", produces = "text/json")
    public ResponseEntity<String> studentDevicesOfferedDevices(
            @RequestParam @NotEmpty String authToken
    ) {
        // TODO update with proper account interface once it's implemented

        DBStudent student;
        try {
            student = new DBStudent(DBUser.Id.AUTH_TOKEN, authToken);
        } catch (NoSuchElementException e) {
            return UNAUTHORIZED(); // invalid student auth
        }

        // returns device ids of student
        ObjectId studentId = student.getObjectId(ID);
        List<String> deviceIds = Device.getDevicesByStudent(studentId);
        // TODO currently returns both offered and accepted device
        //  maybe filter down to offered only, or send as tuple with
        //  device state specified
        return OK(deviceIds.toString());
    }

    /**
     * endpoint to set to all Load student Devices that is offered
     * check documentation for more info on inputs / outputs expected
     */
    @GetMapping ("/api/student/devices/load")
    public String studentDevicesLoad() {
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
        // TODO rework once proper interface is implemented
        DBStudent student;
        Device device;
        try {
            student = new DBStudent(DBUser.Id.AUTH_TOKEN, authToken);
            ObjectId studentId = student.getObjectId(ID);
            device = new Device(deviceId, studentId, false);
        } catch (RuntimeException e) {
            return UNAUTHORIZED();
        }

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
    

