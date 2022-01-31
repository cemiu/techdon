package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

import org.bson.types.ObjectId;

import ac.brunel.techdon.util.db.DBStudent;

public class Student {

	DBStudent dbStudent;
	
	private ObjectId studentId;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String phone;
	private String gender;
	private String address;
	private String postCode;
	
	/**
     * Creates a new student entity, that can
     * be added to the database
     */
	public Student(ObjectId student, String firstName, 
			String lastName, int age, String email, String phone, String gender, 
			String university, String country, String address, String postCode) {
		this.studentId = new ObjectId();
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.postCode = postCode;
		
				
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
}
