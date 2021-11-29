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

}
