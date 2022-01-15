package ac.brunel.techdon.util.db.fields;

public enum DBDeviceField implements DBField {

    DEVICE_ID("_id"), // object id of device
    DEVICE_DONOR("donor_id"), // object id of device's donor
    DEVICE_LISTING_DATE("date_added"), // long, unix time
    DEVICE_TYPE("type"), // device type as string (use DeviceType class for encoding / decoding)
    DEVICE_NAME("name"), // descriptive name of the device
    DEVICE_LOCATION("location"), // string: postcode or city/town
    DEVICE_DESCRIPTION("description"), // optional, string
    DEVICE_ASSIGNED_STUDENT("assigned_student"), // optional, student object id
    DEVICE_ASSIGNED_DATE("assigned_date"), // optional, long, date device was offered to student
    DEVICE_HAS_BEEN_CLAIMED("has_been_claimed"); // boolean

    private final String key;

    DBDeviceField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
