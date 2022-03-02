package ac.brunel.techdon.user;
import ac.brunel.techdon.device.*;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;

import static ac.brunel.techdon.util.db.fields.DBDonorField.*;
import ac.brunel.techdon.util.db.DBDonor;
import ac.brunel.techdon.util.db.DBUser;

public class Donor extends User {
	
	private DBDonor dbDonor;
	private List<ObjectId> donatedDevices;
	
	/**
     * Creates a new donor entity, that can
     * be added to the database
     */
	public Donor(String firstName, String lastName, String email,
			String password, String phone, List<String> address) {
		super(firstName, lastName, email, password, phone, address);
		
		// write to db
		dbDonor = new DBDonor();
		super.init(dbDonor);
		donatedDevices = new ArrayList<>();
	}
	
	public Donor(String auth) {
		dbDonor = new DBDonor(DBUser.Id.AUTH_TOKEN, auth);
		super.load(dbDonor);
		
		// load donor specific from database
		donatedDevices = Device.getDeviceIdsByDonor(userId);
	}

	// TODO: fix getters and setters and add methods
	
	public DBDonor getDbDonor() {
		return dbDonor;
	}

	public void setDbDonor(DBDonor dbDonor) {
		this.dbDonor = dbDonor;
	}

	public List<ObjectId> getDonatedDevices() {
		return dbDonor.getDeviceList();
	}
}
