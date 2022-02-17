package ac.brunel.techdon.util.db.fields;

import java.util.List;

public enum DBUserField implements DBField {

    ID("_id"), // objectId, auto-generated
    USER_ROLE("user_type"), // string, (student / donor, admin)
    EMAIL("email"), // string
    EMAIL_HAS_BEEN_VERIFIED("email_has_been_verified"),
    EMAIL_UPDATED_UNVERIFIED("email_updated_unverified"), // set when user updates their email
                                                              // moved to "email" field upon verification 
    PASSWORD_HASH("password_hash"), // string, sha256 (?)
    PASSWORD_SALT("password_salt"), // string, uuid-4
    CREATION_DATE("creation_date"), // long, unix time
    AUTH_TOKENS("auth_list"), // List<String>
    FIRST_NAME("first_name"), // string
    LAST_NAME("last_name"), // string
    PHONE("phone"), // string
    ADDRESS("address"); // List<String>

    private String key;

    DBUserField(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
