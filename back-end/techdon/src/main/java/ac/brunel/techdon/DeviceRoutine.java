package ac.brunel.techdon;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DevicePreference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
            if (pref != null)
                device.assignToStudent(pref.getStudentId());
            // send email to student, once email is finished
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
