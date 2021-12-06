package ac.brunel.techdon.device;

import static ac.brunel.techdon.util.db.fields.DBDonorField.DeviceField.*;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.Instant;

public class Device {

    // TODO store device donor id as an element of the device, but do not write that field to the database

    // TODO add date added, assigned student, whether it has been claimed, photos, descriptions, etc...
    private ObjectId deviceId;
    private DeviceType type;
    private String name;
    private long date;
    private boolean hasBeenClaimed;

    // optional
    private String description;
    private ObjectId assignedStudent;

    /**
     * Creates a new device listing, that can
     * be added to the database
     */
    public Device(DeviceType type, String name) {
        this.deviceId = new ObjectId();
        this.type = type;
        this.name = name;
        this.date = Instant.now().getEpochSecond();
        this.hasBeenClaimed = false;

        this.description = null;
        this.assignedStudent = null;
    }

    private Device() {}

    public DeviceType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ObjectId getDeviceId() {
        return deviceId;
    }

    /**
     * Returns the device as a Document
     * ready to be written to the database
     */
    public Document toDocument() {
        Document deviceDoc = new Document(DEVICE_ID.getKey(), this.deviceId)
                .append(DEVICE_TYPE.getKey(), this.type.name())
                .append(DEVICE_NAME.getKey(), this.name)
                .append(DEVICE_LISTING_DATE.getKey(), this.date)
                .append(DEVICE_HAS_BEEN_CLAIMED.getKey(), this.hasBeenClaimed);

        if (this.description != null)
            deviceDoc.append(DEVICE_DESCRIPTION.getKey(), this.description);
        if (this.assignedStudent != null)
            deviceDoc.append(DEVICE_ASSIGNED_STUDENT.getKey(), this.assignedStudent);

        return deviceDoc;
    }

    public ObjectId getAssignedStudent() {
        return assignedStudent;
    }

    public void setAssignedStudent(ObjectId assignedStudent) {
        this.assignedStudent = assignedStudent;
    }

    public static Device fromDocument(Document deviceDoc) {
        Device device = new Device();

        device.deviceId = deviceDoc.getObjectId(DEVICE_ID.getKey());
        device.type = DeviceType.typeFromString(deviceDoc.getString(DEVICE_TYPE.getKey()));
        device.name = deviceDoc.getString(DEVICE_NAME.getKey());
        device.date = deviceDoc.getLong(DEVICE_LISTING_DATE.getKey());
        device.hasBeenClaimed = deviceDoc.getBoolean(DEVICE_HAS_BEEN_CLAIMED.getKey());

        // TODO check if it functions as intended when fields are not set
        device.description = deviceDoc.getString(DEVICE_DESCRIPTION.getKey());
        device.assignedStudent = deviceDoc.getObjectId(DEVICE_ASSIGNED_STUDENT.getKey());

        return device;
    }

}
