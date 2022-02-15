package ac.brunel.techdon.device;

import static ac.brunel.techdon.util.db.fields.DBDevicePrefField.*;

import ac.brunel.techdon.util.db.DBDevicePref;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

/**
 * An object that reserve as an interface to {@link ac.brunel.techdon.util.db.DBDevicePref}
 * Every object is an instance of a device preference selection made by
 * a particular students. It functions as a unique association between a student and a device type
 * that stores the date and time of the selection.
 */
public class DevicePreference {

    DBDevicePref dbPref;

    private ObjectId prefId;

    private ObjectId studentId;
    private DeviceType deviceType;
    private long selectionDate;
    private boolean isPrefInQueue;

    private DevicePreference() {}

    public DevicePreference(ObjectId studentId, DeviceType deviceType) {
        this.studentId = studentId;
        this.deviceType = deviceType;
        this.selectionDate = Instant.now().getEpochSecond();
        this.isPrefInQueue = true;

        dbPref = new DBDevicePref();
        prefId = (ObjectId) dbPref.get(PREF_ID);
        dbPref.set(PREF_STUDENT_ID, studentId);
        dbPref.set(PREF_TYPE, deviceType.toString());
        dbPref.set(PREF_DATE, selectionDate);
        dbPref.set(PREF_IS_IN_QUEUE, isPrefInQueue);

        dbPref.setWriteMode(DBWriteMode.AUTOMATIC);
    }

    /**
     * Returns the preference with the associated student
     * that is next in queue to receive a device.
     */
    public static DevicePreference getNextInQueueForDevice(DeviceType deviceType) {
        // TODO
        return null;
    }

    /**
     * Returns the preferences of a particular student,
     * as a list of device types preferred by them.
     */
    public static List<String> getPreferedDevicesByStudent(ObjectId studentId) {
        // TODO
        DBDevicePref.get
        return null;
    }

}
