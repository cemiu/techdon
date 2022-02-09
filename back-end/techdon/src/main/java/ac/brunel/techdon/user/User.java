package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;

import ac.brunel.techdon.util.HashingHelper;
import ac.brunel.techdon.util.db.DBUser;

public class User {
	private DBUser dbUser;
	
	protected ObjectId userId;
	protected String firstName;
	protected String lastName;
	protected String email;
	protected String passwordSalt;
	protected String passwordHash;
	protected String phone;
	protected List<String> address;
	protected long creationDate;
	protected List<String> authTokens;
	
	protected User() {}
	
	/**
	 * Constructor for superclass User,
	 * used in the Student and Donor subclasses
	 */
	public User(String firstName, String lastName,
			String email, String password,
			String phone, 
			List<String> address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.passwordSalt = UUID.randomUUID().toString();
		this.passwordHash = HashingHelper.getHash(password, passwordSalt);
		this.phone = phone;
		this.address = address;
		this.creationDate = Instant.now().getEpochSecond();
		this.authTokens = new ArrayList<>();
	}
	
	protected void init(DBUser dbUser) {
		this.dbUser = dbUser;
		
		userId = (ObjectId) dbUser.get(ID);
		// TODO: write to db
		dbUser.set(EMAIL, email);
		dbUser.set(PASSWORD_HASH, passwordHash);
		dbUser.set(PASSWORD_SALT, passwordSalt);
		dbUser.set(CREATION_DATE, creationDate);
		dbUser.set(AUTH_TOKENS, authTokens);
		dbUser.set(FIRST_NAME, firstName);
		dbUser.set(LAST_NAME, lastName);
		dbUser.set(PHONE, phone);
		dbUser.set(ADDRESS, address);
	}
	
	protected void load(DBUser dbUser) {
		this.dbUser = dbUser;
		
		// load from database
		firstName = dbUser.getString(FIRST_NAME);
		// TODO: all other fields
	}
	
	// TODO: write methods for loggin in, logging out, deleting accounts
	// TODO: fix getters and setters to update account and change in db

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

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}
}
