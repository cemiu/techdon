package ac.brunel.techdon.device;

import static ac.brunel.techdon.util.db.fields.DBDevicePrefField.*;

import ac.brunel.techdon.util.db.DBDevicePref;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import lombok.Getter;
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

    @Getter private ObjectId studentId;
    @Getter private DeviceType deviceType;
    private long selectionDate;
    private boolean isPrefInQueue;

    @SuppressWarnings("unused")
    private DevicePreference() {}

    /**
     * Loads a particular preference from the database.
     * If the preference does not exist, it is created, if
     * the creation flag is set to true.
     */
    public DevicePreference(ObjectId studentId, DeviceType deviceType, boolean createIfNotExists) {
        // 1: attempt to load
        dbPref = new DBDevicePref(studentId, deviceType);
        if (dbPref.doesExistInDB()) {
            load();
            return;
        }

        if (!createIfNotExists)
            throw new IllegalArgumentException("Preference for " + deviceType
                    + " does not exist by " + studentId.toString());

        // 2: if not found, create
        dbPref = new DBDevicePref();
        this.studentId = studentId;
        this.deviceType = deviceType;
        this.selectionDate = Instant.now().getEpochSecond();
        this.isPrefInQueue = true;

        dbPref = new DBDevicePref();
        dbPref.set(PREF_STUDENT_ID, studentId);
        dbPref.set(PREF_TYPE, deviceType.toString());
        dbPref.set(PREF_DATE, selectionDate);
        dbPref.set(PREF_IS_IN_QUEUE, isPrefInQueue);

        dbPref.setWriteMode(DBWriteMode.AUTOMATIC);
    }

    /**
     * Loads a preference file, given an
     * arbitrary db object.
     */
    public DevicePreference(DBDevicePref dbPref) {
        this.dbPref = dbPref;
        load();
    }

    /**
     * Loads all fields from the remote object.
     */
    private void load() {
        this.studentId = dbPref.getObjectId(PREF_STUDENT_ID);
        this.deviceType = DeviceType.typeFromString(dbPref.getString(PREF_TYPE));
        this.selectionDate = dbPref.getLong(PREF_DATE);
        this.isPrefInQueue = dbPref.getBoolean(PREF_IS_IN_QUEUE);
    }

    /**
     * Sets whether the preference is in the queue
     * for the device assignment (if a device is offered,
     * it is removed from the queue).
     */
    public void setPrefIsInQueue(boolean isPrefInQueue) {
        this.isPrefInQueue = isPrefInQueue;
        dbPref.set(PREF_IS_IN_QUEUE, isPrefInQueue);
    }

    /**
     * Resets the selection date to the current time.
     * (Moves student to the end of the queue.)
     */
    public void resetSelectionDate() {
        selectionDate = Instant.now().getEpochSecond();
        dbPref.set(PREF_DATE, selectionDate);
    }

    /**
     * Deletes the preference
     */
    public void removePreference() {
        dbPref.delete();
    }

    /**
     * Returns the preference with the associated student
     * that is next in queue to receive a device.
     * Returns null if no student is in queue.
     */
    public static DevicePreference getNextInQueueForDevice(DeviceType deviceType) {
        DBDevicePref dbPref = DBDevicePref.getNextInQueueForDevice(deviceType);
        if (dbPref == null)
            return null;
        return new DevicePreference(dbPref);
    }

    /**
     * Returns the preferences of a particular student,
     * as a list of device types preferred by them.
     */
    public static List<String> getPreferredDevicesByStudent(ObjectId studentId) {
        return DBDevicePref.getPreferredDevicesByStudent(studentId);
    }

}
