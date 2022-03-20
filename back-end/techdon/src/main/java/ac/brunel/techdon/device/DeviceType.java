package ac.brunel.techdon.device;

public enum DeviceType {

    // TODO expand list with all options offered on our platform

    LAPTOP("laptop"),
    DESKTOP_COMPUTER("desktopComputer"),
    SMARTPHONE("smartphone"),
    IPAD("iPad"),
    TABLET("tablet"),
    PRINTER("printer"),
    MONITOR("monitor"),
    HARD_DRIVE("hardDrive"),
    KEYBOARD("keyboard"),
    MOUSE("mouse"),
    USB_STICK("usbStick");

    private final String name;

    DeviceType(String name) {
        this.name = name;
    }

    /**
     * Returns a device type given the type's descriptor string
     */
    public static DeviceType typeFromStringSafe(String type) {
        return switch (type) {
            case "laptop" -> LAPTOP;
            case "desktopComputer" -> DESKTOP_COMPUTER;
            case "smartphone" -> SMARTPHONE;
            case "iPad" -> IPAD;
            case "tablet" -> TABLET;
            case "printer" -> PRINTER;
            case "monitor" -> MONITOR;
            case "hardDrive" -> HARD_DRIVE;
            case "keyboard" -> KEYBOARD;
            case "mouse" -> MOUSE;
            case "usbStick" -> USB_STICK;
            default -> null;
        };
    }

    public static DeviceType typeFromString(String type) {
        DeviceType deviceType = typeFromStringSafe(type);
        if (deviceType == null)
            throw new IllegalArgumentException(type + " is not a valid device type.");
        return deviceType;
    }

    @Override
    public String toString() {
        return name;
    }

}
