package ac.brunel.techdon.device;

public class Device {

    // TODO add date added, assigned student, whether it has been claimed, photos, descriptions, etc...
    private String deviceId;
    private DeviceType type;
    private String name;

    public Device(DeviceType type, String name) {
        this.type = type;
        this.name = name;
        this.deviceId = null;
    }

    public DeviceType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDeviceId() {
        return deviceId;
    }

    // Device IDs should be automatically generated UUIDs
    // which are checked for collisions in the DB
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
