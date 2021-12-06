package ac.brunel.techdon.util.db.fields;

/**
 * Defines the fields which are specific to a remote
 * {@link ac.brunel.techdon.util.db.DBDonor} object
 */
public enum DBDonorField implements DBField {

    DONATED_DEVICES("donated_devices"); // array of type Device

    private String key;

    DBDonorField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static enum DeviceField {
        DEVICE_ID("device_id"),
        DEVICE_TYPE("type"),
        DEVICE_NAME("name"),
        DEVICE_LISTING_DATE("date_added"), // long, unix time
        DEVICE_DESCRIPTION("description"), // optional
        DEVICE_ASSIGNED_STUDENT("assigned_student"), // student foreign key (object id)
        DEVICE_HAS_BEEN_CLAIMED("has_been_claimed");
        String key;

        DeviceField(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return getKey();
        }
    }

}
