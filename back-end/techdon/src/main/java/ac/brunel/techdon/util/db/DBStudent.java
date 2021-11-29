package ac.brunel.techdon.util.db;

import ac.brunel.techdon.util.db.fields.DBStudentField;

import java.util.NoSuchElementException;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

public class DBStudent extends DBUser {

    public DBStudent() {
        super();
        set(USER_ROLE, "student");
    }

    public DBStudent(Id idMode, String id) {
        super(idMode, id);
        if (doesExistInDB() && getString(USER_ROLE).equals("student"))
            return;

        // user couldn't be loaded
        throw new NoSuchElementException("Could not load user with " + idMode + ": " + id + ", as a student");
    }

    public void set(DBStudentField field, Object value) {
        set(field.getKey(), value);
    }

}
