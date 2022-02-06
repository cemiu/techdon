package ac.brunel.techdon.user;
import ac.brunel.techdon.device.*;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.util.List;

import org.bson.types.ObjectId;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import ac.brunel.techdon.util.db.DBDonor;

public class Donor extends User {
	
	DBDonor dbDonor;
	private ObjectId donorId;	
	private List<Device> donatedDevices;
	
	/**
     * Creates a new donor entity, that can
     * be added to the database
     */
	public Donor(ObjectId donorId, String firstName, String lastName, int age, String email,
			String phone, String gender, String address, String postCode) {
		super(firstName, lastName, age, email, phone, gender, address, postCode);
		this.donorId = donorId;
		
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

	public List<ObjectId> getDonatedDevices() {
		return dbDonor.getDeviceList();
	}

	public void setDonatedDevices(List<Device> donatedDevices) {
		dbDonor.set(DONATED_DEVICES, donatedDevices);
	}
	
	
}
