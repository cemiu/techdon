package ac.brunel.techdon.util.db;

import ac.brunel.techdon.util.db.fields.DBStudentField;
import ac.brunel.techdon.util.db.fields.DBUserField;

import java.util.NoSuchElementException;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

/**
 * A specialization of {@link DBUser}, used to instantiate new
 * remote student objects, and accessing student specific functionality
 */
public class DBStudent extends DBUser {

    /**
     * Used to create new student objects. After instantiating,
     * fill all required fields using {@link #set(DBStudentField, Object)}
     * and {@link #set(ac.brunel.techdon.util.db.fields.DBUserField, Object)},
     * then call the {@link #write()} method, to push new object to database.
     * After the initial write, the object has to be discarded, as further
     * modification attempts will not be possible!
     */
    public DBStudent() {
        super();
        set(USER_ROLE, "student");
    }

    /**
     * Loads a remote student object from the database, by using
     * their objectId, email, or auth token for identification
     * @throws NoSuchElementException when no student with matching
     * criteria can be found (i.e. user is not a student, or does not exist)
     */
    public DBStudent(Id idMode, String id) {
        super(idMode, id);
        if (doesExistInDB() && getString(USER_ROLE).equals("student"))
            return;

        // user couldn't be loaded
        throw new NoSuchElementException("Could not load user with " + idMode + ": " + id + ", as a student");
    }

    /**
     * Used to set student specific fields, use
     * {@link #set(DBUserField, Object)} for general user fields
     */
    public void set(DBStudentField field, Object value) {
        set(field.getKey(), value);
    }

    public static DBStudent loadStudent(Id idMode, String id) {
        try {
            return new DBStudent(idMode, id);
        } catch (Exception e) {
            return null; // no student with matching criteria
        }
    }

}
