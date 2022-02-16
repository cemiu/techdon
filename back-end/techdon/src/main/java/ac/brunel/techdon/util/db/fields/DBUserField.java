package ac.brunel.techdon.util.db.fields;

public enum DBUserField implements DBField {

    ID("_id"), // objectId, auto-generated
    USER_ROLE("user_type"), // string, (student / donor, admin)
    EMAIL("email"), // string
    PASSWORD_HASH("password_hash"), // string, sha256 (?)
    PASSWORD_SALT("password_salt"), // string, uuid-4
    CREATION_DATE("creation_date"), // long, unix time
    AUTH_TOKENS("auth_list"), // List<String>
    FIRST_NAME("first_name"), // string
    LAST_NAME("last_name"), // string
    PHONE("phone"), // string
    ADDRESS("address"); // List<String>

    private final String key;

    DBUserField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
