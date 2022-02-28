package ac.brunel.techdon.device;

import static ac.brunel.techdon.util.db.fields.DBDeviceField.*;

import ac.brunel.techdon.util.db.DBDevice;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.security.access.AccessDeniedException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class Device {

    DBDevice dbDevice;

    // TODO add photo support later
    private ObjectId deviceId;

    private ObjectId donorId;
    private DeviceType type;
    private String name;
    private long listingDate;

    // optional
    private String location; // only needed for in-person collection
    private String description;
    private ObjectId assignedStudent;
    private Long assignedDate;
    private boolean hasBeenClaimed;

    @SuppressWarnings("unused")
    private Device() {}

    /**
     * Creates a new device listing, that can
     * be added to the database
     */
    public Device(ObjectId donor, DeviceType type, String name) {
        this.deviceId = new ObjectId();

        this.donorId = donor;
        this.type = type;
        this.name = name;
        this.listingDate = Instant.now().getEpochSecond();

        this.location = null;
        this.description = null;

        this.assignedStudent = null;
        this.assignedDate = null;
        this.hasBeenClaimed = false;

        // write to db
        dbDevice = new DBDevice();
        deviceId = (ObjectId) dbDevice.get(DEVICE_ID);
        dbDevice.set(DEVICE_DONOR, donor);
        dbDevice.set(DEVICE_TYPE, type.toString());
        dbDevice.set(DEVICE_NAME, name);
        dbDevice.set(DEVICE_LISTING_DATE, listingDate);

        // skip values which are null
        dbDevice.setWriteMode(DBWriteMode.AUTOMATIC);
    }

    /**
     * Loads a device from the database,
     * given its object id in string form
     */
    public Device(String deviceId) {
        this(new ObjectId(deviceId));
    }

    /**
     * Loads a device, belonging to a particular student / donor,
     * Set the userId to the user's object id, and isDonor to true
     * or false depending on whether the queried user is a student or donor
     */
    public Device(String deviceId, ObjectId userId, boolean isDonor) {
        this(new ObjectId(deviceId));
        if (!userId.equals(isDonor ? donorId : assignedStudent))
            throw new AccessDeniedException("Tried to load device " + deviceId +
                    " for " + (isDonor ? "donor" : "student") + " " + userId +
                    ", but it does not belong to them.");
    }

    /**
     * Loads a device from the database,
     * given its object id
     */
    private Device(ObjectId deviceId) {
        // load device from database
        dbDevice = new DBDevice(deviceId);
        dbDevice.setWriteMode(DBWriteMode.AUTOMATIC);

        // throws error if no device exists
        if (!dbDevice.doesExistInDB())
            throw new NoSuchElementException("Tried to load device with id " + deviceId +
                    "but could not find it in the database");

        // populates all values
        this.deviceId = deviceId;
        donorId = dbDevice.getObjectId(DEVICE_DONOR);
        listingDate = dbDevice.getLong(DEVICE_LISTING_DATE);
        type = DeviceType.typeFromString(dbDevice.getString(DEVICE_TYPE));
        name = dbDevice.getString(DEVICE_NAME);

        // optional, set to null if not found in db
        location = dbDevice.getString(DEVICE_LOCATION);
        description = dbDevice.getString(DEVICE_DESCRIPTION);
        assignedStudent = dbDevice.getObjectId(DEVICE_ASSIGNED_STUDENT);
        if (assignedStudent == null) {
            assignedDate = null;
            hasBeenClaimed = false;
        } else {
            assignedDate = dbDevice.getLong(DEVICE_ASSIGNED_DATE);
            hasBeenClaimed = dbDevice.getBoolean(DEVICE_HAS_BEEN_CLAIMED);
        }
    }

    /**
     * Returns the object id of the device
     */
    public ObjectId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the donor user's object id
     */
    @SuppressWarnings("unused")
    public ObjectId getDonorId() {
        return donorId;
    }

    /**
     * Returns the type of the device
     */
    public DeviceType getType() {
        return type;
    }

    /**
     * Returns the descriptive name of a device
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether the device has a location
     * for in-person collection
     */
    public boolean hasLocation() {
        return location != null;
    }

    /**
     * Returns the device's location.
     * Returns null if no location is set
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns whether the device has been given a description
     */
    @SuppressWarnings("unused")
    public boolean hasDescription() {
        return description != null;
    }

    /**
     * Returns the device's description.
     * Returns null if no description has been set
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the device has been assigned
     * to as student
     */
    public boolean isAssigned() {
        return assignedStudent != null;
    }

    /**
     * Returns assigned student's object id
     * If the device is not assigned, returns null
     */
    @SuppressWarnings("unused")
    public ObjectId getAssignedStudent() {
        return assignedStudent;
    }

    /**
     * Returns the date on which the device
     * was offered to a student. If the device
     * hasn't been offered, it returns -1
     */
    public long getAssignedDate() {
        if (assignedDate == null)
            return -1;
        return assignedDate;
    }

    /**
     * Returns whether the device has been claimed by a
     * student. If no student is assigned, returns false
     */
    @SuppressWarnings("unused")
    public boolean hasBeenClaimed() {
        return hasBeenClaimed;
    }

    /**
     * Updates the device type of a device
     */
    public void setType(DeviceType type) {
        this.type = type;
        dbDevice.set(DEVICE_TYPE, type.toString());
    }

    /**
     * Updates the name of the device
     */
    public void setName(String name) {
        this.name = name;
        dbDevice.set(DEVICE_NAME, name);
    }

    /**
     * Sets, updates, or removes the location
     * of a device
     */
    public void setLocation(String location) {
        if (description == null || description.equals("")) {
            removeLocation();
            return;
        }
        this.location = location;
        dbDevice.set(DEVICE_LOCATION, location);
    }

    /**
     * Sets or updates the description of a device
     */
    public void setDescription(String description) {
        if (description == null || description.equals("")) {
            removeDescription();
            return;
        }
        this.description = description;
        dbDevice.set(DEVICE_DESCRIPTION, description);
    }

    /**
     * Assigns / reassigns the device to a student,
     * expects that the user is in the database already
     */
    public void assignToStudent(ObjectId studentId) {
        if (studentId == null) {
            unassign();
            return;
        }
        this.assignedStudent = studentId;
        this.assignedDate = Instant.now().getEpochSecond();
        this.hasBeenClaimed = false;

        // TODO for multiple sequential writes, maybe set write mode to manual temporarily ?
        dbDevice.set(DEVICE_ASSIGNED_STUDENT, assignedStudent);
        dbDevice.set(DEVICE_ASSIGNED_DATE, assignedDate);
        dbDevice.set(DEVICE_HAS_BEEN_CLAIMED, hasBeenClaimed);
    }

    /**
     * Sets a device's status as claimed,
     * expects that it has been assigned to student
     */
    @SuppressWarnings("unused")
    public void setClaimed() {
        setClaimed(true);
    }

    /**
     * Sets the device as either claimed or unclaimed
     */
    @SuppressWarnings("unused")
    private void setClaimed(boolean status) {
        if (assignedStudent == null)
            throw new IllegalArgumentException("Cannot (un)claim device that is not" +
                    "assigned to a student. For device: " + deviceId);

        hasBeenClaimed = status;
        dbDevice.set(DEVICE_HAS_BEEN_CLAIMED, hasBeenClaimed);
    }

    /**
     * Removes the assigned student for the device
     */
    public void unassign() {
        assignedStudent = null;
        assignedDate = null;
        hasBeenClaimed = false;

        dbDevice.remove(DEVICE_ASSIGNED_STUDENT);
        dbDevice.remove(DEVICE_ASSIGNED_DATE);
        dbDevice.remove(DEVICE_HAS_BEEN_CLAIMED);
    }

    /**
     * Removes the location of a device
     */
    public void removeLocation() {
        location = null;
        dbDevice.remove(DEVICE_LOCATION);
    }

    /**
     * Deletes the description of a device
     */
    public void removeDescription() {
        description = null;
        dbDevice.remove(DEVICE_DESCRIPTION);
    }
    
    /**
     * Deletes device from db
     */
    public void deleteDevice() {
        dbDevice.delete();
    }

    /**
     * Method that takes in a donor's object id and returns
     * a list of all device ids donated by them
     */
    public static List<ObjectId> getDeviceIdsByDonor(ObjectId donorId) {
        if (donorId == null)
            return null;
        return DBDevice.getDevicesByUser(donorId, true, Optional.empty());
    }
    
    /**
     * Returns a list of all devices
     * belonging to a donor
     */
    public static List<String> getDevicesByDonor(ObjectId donorId) {
        List<String> list = new ArrayList<>();
        getDeviceIdsByDonor(donorId).stream().map(ObjectId::toString)
                .forEach(list::add);
        return list;
    }

    /**
     * Method that takes in a student's object id and returns
     * a list of all device ids offered to or accepted by them
     */
    public static List<ObjectId> getDeviceIdsByStudent(ObjectId studentId) {
        return DBDevice.getDevicesByUser(studentId, false, Optional.empty());
    }

    /**
     * Returns a list of device ids belonging to a student
     * in string format
     */
    public static List<String> getDevicesByStudent(ObjectId studentId, Optional<Boolean> hasBeenClaimed) {
        List<String> list = new ArrayList<>();
        getDeviceIdsByStudent(studentId).stream().map(ObjectId::toString)
                .forEach(list::add);
        return list;
    }

    /**
     * Returns a JSON representation of the device
     */
    public Document toDoc() {
        Document deviceDoc = new Document("deviceId", deviceId)
                .append("deviceType", getType())
                .append("deviceName", getName());

        if (hasDescription())
            deviceDoc.append("deviceDescription", getDescription());
        if (hasLocation())
            deviceDoc.append("deviceLocation", getLocation());

        return deviceDoc;
    }
}
