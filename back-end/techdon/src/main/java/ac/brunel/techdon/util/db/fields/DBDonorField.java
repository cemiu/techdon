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

}
