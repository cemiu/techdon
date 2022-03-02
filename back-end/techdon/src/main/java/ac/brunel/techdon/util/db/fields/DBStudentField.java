package ac.brunel.techdon.util.db.fields;

/**
 * Specifies fields which are specific to a remote
 * {@link ac.brunel.techdon.util.db.DBStudent} object
 */
public enum DBStudentField implements DBField {

    UNIVERSITY("university"); // string

    private final String key;

    DBStudentField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
