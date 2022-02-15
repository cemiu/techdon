package ac.brunel.techdon.util.db.fields;

public enum DBDevicePrefField implements DBField {

    PREF_ID("_id"), // ObjectId, PK
    PREF_STUDENT_ID("student_id"), // ObjectId, FK
    PREF_TYPE("device_type"), // string
    PREF_DATE("selection_date"), // long, unix time
    PREF_IS_IN_QUEUE("is_in_queue"); // boolean, whether the preference is in the queue

    private final String key;

    DBDevicePrefField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
