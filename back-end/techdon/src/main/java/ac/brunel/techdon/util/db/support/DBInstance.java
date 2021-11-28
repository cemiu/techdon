package ac.brunel.techdon.util.db.support;

public interface DBInstance {

    public DBWriteMode getWriteMode();
    public void setWriteMode(DBWriteMode newMode);
    public void write();
    public Object get(String key);

    /**
     * Returns value of specified types
     */
    public default <T> T get(String key, Class<T> t) {
        Object obj = get(key);
        if(!t.isInstance(obj))
            return null;
        return t.cast(obj);
    }

    public default String getString(String key) {
        return get(key, String.class);
    }

    public default Integer getInt(String key) {
        return get(key, Integer.class);
    }

    public default Boolean getBoolean(String key) {
        return get(key, Boolean.class);
    }

}
