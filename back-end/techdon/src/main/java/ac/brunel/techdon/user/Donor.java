package ac.brunel.techdon.user;
import ac.brunel.techdon.device.*;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.util.List;

import org.bson.types.ObjectId;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import ac.brunel.techdon.util.db.DBDonor;

public class Donor {
	
	DBDonor dbDonor;
	
	private ObjectId donorId;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String phone;
	private String gender;
	private String address;
	private String postCode;
	private List<Device> donatedDevices;
	
	/**
     * Creates a new donor entity, that can
     * be added to the database
     */
	public Donor(ObjectId donorId, String firstName, String lastName, int age, String email,
			String phone, String gender, String address, String postCode) {
		this.donorId = donorId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.phone = phone;
		this.gender = gender;
		this.address = address;
		this.postCode = postCode;
		
		// write to db
		dbDonor = new DBDonor();
		dbDonor.set(DONATED_DEVICES, donatedDevices);
	}

	// TODO: fix getters and setters and add methods
	
	public DBDonor getDbDonor() {
		return dbDonor;
	}

	public void setDbDonor(DBDonor dbDonor) {
		this.dbDonor = dbDonor;
	}

	public ObjectId getDonorId() {
		return donorId;
	}

	public void setDonorId(ObjectId donorId) {
		this.donorId = donorId;
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

	public List<ObjectId> getDonatedDevices() {
		return dbDonor.getDeviceList();
	}

	public void setDonatedDevices(List<Device> donatedDevices) {
		dbDonor.set(DONATED_DEVICES, donatedDevices);
	}
	
	
}
