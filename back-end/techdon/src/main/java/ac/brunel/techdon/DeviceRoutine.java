package ac.brunel.techdon;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DevicePreference;
import ac.brunel.techdon.util.EmailHelper;
import ac.brunel.techdon.util.db.DBStudent;
import ac.brunel.techdon.util.db.DBUser;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;

@Component
public class DeviceRoutine {

    /**
     * Assigns available devices to next student in queue
     * Automatically runs every minute
     */
    @Scheduled(fixedRate = 60000)
    public void distributeDevicesThroughQueue() {
        for (Device device : Device.getUnassignedDevices()) {
            DevicePreference pref = DevicePreference.getNextInQueueForDevice(device.getType());
            if (pref == null)
                continue;
            DBStudent student = DBStudent.loadStudent(DBUser.Id.USER_ID, pref.getStudentId().toString());
            try {
                SimpleMailMessage mail = EmailHelper.getNewOfferEmail(student.getString(EMAIL), student.getString(FIRST_NAME), pref.getDeviceType().toString());
                EmailHelper.sendEmail(mail);
            } catch (Exception e) {}


            device.assignToStudent(pref.getStudentId());
        }
    }

    /**
     * Revokes devices from students who have not
     * claimed them in time (2 days)
     * Automatically runs every 30 minutes
     */
    @Scheduled(fixedRate = 60000 * 30)
    public void revokeUnassignedDevices() {

    }

}
