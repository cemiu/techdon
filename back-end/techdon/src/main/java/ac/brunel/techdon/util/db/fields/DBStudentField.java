package ac.brunel.techdon.util.db.fields;

public enum DBStudentField implements DBField {

    UNIVERSITY("student/university"),
    COUNTRY("student/country"),
    DEVICE_SELECTION("student/[deviceSelection]"), // TODO add interface support for
    //DEVICE_SELECTION_TYPE("student/[deviceSelection]/type"), //TODO document arrays !!
    //DEVICE_SELECTION_DATE("student/[deviceSelection]/date"),
    DEVICES_OFFERED("student/devicesOffered"); // string array (or custom type?)

    private String key;

    DBStudentField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
