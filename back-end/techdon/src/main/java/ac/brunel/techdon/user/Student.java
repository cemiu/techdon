package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.util.List;

import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

import org.bson.types.ObjectId;

import ac.brunel.techdon.util.db.DBStudent;

public class Student extends User {

	DBStudent dbStudent;
	//private List<DeviceType> selectedDevices;

	/**
     * Creates a new student entity, that can
     * be added to the database
     */
	public Student(ObjectId student, String firstName, 
			String lastName, String email, String password, String phone, 
			String university, String country, List<String> address) {
		super(firstName, lastName, email, password, phone, address);
						
		// TODO: write to db
		dbStudent = new DBStudent();
		super.init(dbStudent);
		dbStudent.set(UNIVERSITY, university);
		dbStudent.set(COUNTRY, country);
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
		return dbStudent.getString(UNIVERSITY);
	}

	public void setUniversity(String university) {
		dbStudent.set(UNIVERSITY, university);
	}

	public String getCountry() {
		return dbStudent.getString(COUNTRY);
	}

	public void setCountry(String country) {
		dbStudent.set(COUNTRY, country);
	}
}
