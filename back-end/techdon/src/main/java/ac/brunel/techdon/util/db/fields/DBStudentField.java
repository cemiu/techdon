package ac.brunel.techdon.util.db.fields;

/**
 * Specifies fields which are specific to a remote
 * {@link ac.brunel.techdon.util.db.DBStudent} object
 */
public enum DBStudentField implements DBField {

    UNIVERSITY("student/university"), // string
    COUNTRY("student/country"), // string, required (?)
    DEVICE_SELECTION("student/deviceSelection"), // array<object> TODO add interface support for
    DEVICES_OFFERED("student/devicesOffered"); // array<string> (or custom type?)

    private String key;

    DBStudentField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public static enum SelectionField {

        DEVICE_SELECTION_TYPE("type"),
        DEVICE_SELECTION_DATE("selection_data"); // unix time, long

        private String key;

        SelectionField(String key) {
            this.key = key;
        }

    }

}
