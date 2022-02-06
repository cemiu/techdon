package ac.brunel.techdon.user;

import org.bson.types.ObjectId;

public class User {
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String phone;
	private String gender;
	private String address;
	private String postCode;
	private boolean loggedIn;
	
	/**
	 * Constructor for superclass User,
	 * used in the Student and Donor subclasses
	 */
	public User(String firstName, String lastName, int age, 
			String email, String phone, String gender, 
			String address, String postCode) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.address = address;
		this.postCode = postCode;
		this.loggedIn = false;
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

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
