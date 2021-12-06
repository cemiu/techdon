package ac.brunel.techdon.util.db.support;

import ac.brunel.techdon.util.db.fields.DBField;

public interface DBInstance {

    public DBWriteMode getWriteMode();

    public void setWriteMode(DBWriteMode newMode);

    public void write();

    public Object get(String key);

    public default Object get(DBField field) {
        return get(field.getKey());
    }

    /**
     * Returns value of specified types
     */
    public default <T> T get(String key, Class<T> t) {
        Object obj = get(key);
        if(!t.isInstance(obj))
            return null;
        return t.cast(obj);
    }

    public default <T> T get(DBField field, Class<T> t) {
        return get(field.getKey(), t);
    }

    public default String getString(String key) {
        return get(key, String.class);
    }

    public default String getString(DBField field) {
        return getString(field.getKey());
    }

    public default Integer getInt(String key) {
        return get(key, Integer.class);
    }

    public default Integer getInt(DBField field) {
        return getInt(field.getKey());
    }

    public default Boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    public default Boolean getBoolean(DBField field) {
        return getBoolean(field.getKey());
    }

}
