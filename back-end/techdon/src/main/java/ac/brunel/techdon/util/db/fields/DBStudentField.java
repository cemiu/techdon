package ac.brunel.techdon.util.db.fields;

/**
 * Specifies fields which are specific to a remote
 * {@link ac.brunel.techdon.util.db.DBStudent} object
 */
public enum DBStudentField implements DBField {

    UNIVERSITY("university"), // string
    COUNTRY("country"), // string, required (?)
    DEVICE_SELECTION("deviceSelection"), // array<object> TODO add interface support for
    DEVICES_OFFERED("devicesOffered"); // array<string> (or custom type?)

    private final String key;

    DBStudentField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public enum SelectionField {

        DEVICE_SELECTION_TYPE("type"),
        DEVICE_SELECTION_DATE("selection_data"); // unix time, long

        private final String key;

        SelectionField(String key) {
            this.key = key;
        }

    }

}
