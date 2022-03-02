package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.util.List;

import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

import org.bson.types.ObjectId;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBStudent;
import ac.brunel.techdon.util.db.DBUser;

public class Student extends User {

	private DBStudent dbStudent;
	private String university;
	// private List<DeviceType> selectedDevices;

	/**
	 * Creates a new student entity, that can be added to the database
	 */
	public Student(String firstName, String lastName, String email, String password, String phone, List<String> address,
			String university) {
		super(firstName, lastName, email, password, phone, address);

		// TODO: write to db
		dbStudent = new DBStudent();
		dbStudent.set(UNIVERSITY, university);
		super.init(dbStudent);
	}

	public Student(String auth) {
		dbStudent = new DBStudent(DBUser.Id.AUTH_TOKEN, auth);
		super.load(dbStudent);
	}

	// TODO: fix getters and setters & add methods
	// TODO: load from db (check donor class)

	public DBStudent getDbStudent() {
		return dbStudent;
	}

	public void setDbStudent(DBStudent dbStudent) {
		this.dbStudent = dbStudent;
	}

	public String getUniversity() {
		return this.university;
	}

	public void setUniversity(String university) {
		this.university = university;
		dbStudent.set(UNIVERSITY, university);
	}

}
