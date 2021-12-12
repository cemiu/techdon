package ac.brunel.techdon.device;

public enum DeviceType {

    // TODO expand list with all options offered on our platform

    LAPTOP("laptop"),
    DESKTOP_COMPUTER("desktopComputer"),
    IPAD("iPad"),
    MONITOR("monitor"),
    HARD_DRIVE("hardDrive");

    private String name;

    DeviceType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Returns a device type given the type's descriptor string
     */
    public static DeviceType typeFromString(String type) {
        switch (type) {
            case "laptop":
                return LAPTOP;
            case "desktopComputer":
                return DESKTOP_COMPUTER;
            case "iPad":
                return IPAD;
            case "monitor":
                return MONITOR;
            case "hardDrive":
                return HARD_DRIVE;
            default:
                return null;
        }
    }

}
