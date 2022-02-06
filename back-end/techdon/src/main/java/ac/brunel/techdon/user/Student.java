package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

import org.bson.types.ObjectId;

import ac.brunel.techdon.util.db.DBStudent;

public class Student extends User {

	DBStudent dbStudent;
	private ObjectId studentId;

	/**
     * Creates a new student entity, that can
     * be added to the database
     */
	public Student(ObjectId student, String firstName, 
			String lastName, int age, String email, String phone, String gender, 
			String university, String country, String address, String postCode) {
		super(firstName, lastName, age, email, phone, gender, address, postCode);
		this.studentId = new ObjectId();
						
		// TODO: write to db
		dbStudent = new DBStudent();
		dbStudent.set(UNIVERSITY, university);
		dbStudent.set(COUNTRY, country);
	}
	
	// TODO: fix getters and setters & add methods

	public DBStudent getDbStudent() {
		return dbStudent;
	}

	public void setDbStudent(DBStudent dbStudent) {
		this.dbStudent = dbStudent;
	}

	public ObjectId getStudentId() {
		return studentId;
	}

	public void setStudentId(ObjectId studentId) {
		this.studentId = studentId;
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
