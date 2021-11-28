package ac.brunel.techdon.util.db.fields;

import java.util.List;

public enum DBUserField implements DBFields {

    ID("_id", String.class),
    FIRST_NAME("firstName", String.class),
    LAST_NAME("lastName", String.class),
    ROLE("role", String.class),
    CREATION_DATE("creationDate", Long.class),
    AUTH_TOKENS("auth_list", List.class),
    EMAIL("email", String.class),
    PHONE("phone", String.class),
    ADDRESS("address", List.class);

    private String key;
    private Class type;

    <T> DBUserField(String key, T t) {
        this.key = key;
        this.type = (Class) t;
    }

    public String getKey() {
        return key;
    }

    public <T> T getType() {
        return (T) type;
    }
}
