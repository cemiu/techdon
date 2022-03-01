package ac.brunel.techdon.util.db.support;

import ac.brunel.techdon.util.db.fields.DBField;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

public interface DBInstance {

    DBWriteMode getWriteMode();

    void setWriteMode(DBWriteMode newMode);

    void write();

    Object get(String key);

    default Object get(DBField field) {
        return get(field.getKey());
    }

    /**
     * Returns value of specified types
     */
    default <T> T get(String key, Class<T> t) {
        Object obj = get(key);
        return t.isInstance(obj) ? t.cast(obj) : null;
    }

    default <T> T get(DBField field, Class<T> t) {
        return get(field.getKey(), t);
    }

    default String getString(String key) {
        return get(key, String.class);
    }

    default String getString(DBField field) {
        return getString(field.getKey());
    }

    default Integer getInt(String key) {
        return get(key, Integer.class);
    }

    default Integer getInt(DBField field) {
        return getInt(field.getKey());
    }

    default Long getLong(String key) {
        return get(key, Long.class);
    }

    default Long getLong(DBField field) {
        return getLong(field.getKey());
    }

    default Boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

    default Boolean getBoolean(DBField field) {
        return getBoolean(field.getKey());
    }

    default ObjectId getObjectId(String key) {
        return get(key, ObjectId.class);
    }

    default ObjectId getObjectId(DBField field) {
        return getObjectId(field.getKey());
    }

    default <T> List<T> getList(String key, Class<T> t) {
        List list = get(key, List.class);
        return list != null ? list : new ArrayList<>();
    }

    default <T> List<T> getList(DBField field, Class<T> t) {
            return getList(field.getKey(), t);
    }

}
