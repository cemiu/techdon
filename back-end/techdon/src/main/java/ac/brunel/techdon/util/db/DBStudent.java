package ac.brunel.techdon.util.db;

import ac.brunel.techdon.util.db.fields.DBStudentField;

public class DBStudent extends DBUser {

    public DBStudent() {
        super();
        // next developer has to populate all fields, and manually write to db
    }

    public void set(DBStudentField field, Object value) {
        set(field.getKey(), value);
    }

}
