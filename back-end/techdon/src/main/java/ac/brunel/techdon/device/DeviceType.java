package ac.brunel.techdon.device;

public enum DeviceType {

    // TODO expand list with all options offered on our platform

    LAPTOP("laptop"),
    DESKTOP_COMPUTER("desktopComputer"),
    IPAD("iPad"),
    MONITOR("monitor"),
    HARD_DRIVE("hardDrive");

    private final String name;

    DeviceType(String name) {
        this.name = name;
    }

    /**
     * Returns a device type given the type's descriptor string
     */
    public static DeviceType typeFromString(String type) {
        return switch (type) {
            case "laptop" -> LAPTOP;
            case "desktopComputer" -> DESKTOP_COMPUTER;
            case "iPad" -> IPAD;
            case "monitor" -> MONITOR;
            case "hardDrive" -> HARD_DRIVE;
            default -> throw new IllegalArgumentException(type + " is not a valid device type.");
        };
    }

    @Override
    public String toString() {
        return name;
    }

}
