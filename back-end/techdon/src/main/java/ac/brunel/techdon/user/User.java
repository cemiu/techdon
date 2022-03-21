package ac.brunel.techdon.user;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bson.types.ObjectId;

import ac.brunel.techdon.util.SecurityHelper;
import ac.brunel.techdon.util.db.DBUser;
import ac.brunel.techdon.util.db.support.DBWriteMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;



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

	protected User() {
		
	}

	/**
	 * Constructor for superclass User, used in the Student and Donor subclasses
	 */
	protected User(String firstName, String lastName, String email, String password, String phone,
			List<String> address) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email.toLowerCase();
		this.passwordSalt = UUID.randomUUID().toString();
		this.passwordHash = SecurityHelper.getHash(password, passwordSalt);
		this.phone = phone;
		this.address = address;
		this.creationDate = Instant.now().getEpochSecond();
		this.authTokens = new ArrayList<>();
	}

	protected void init(DBUser dbUser) {
		this.dbUser = dbUser;

		userId = (ObjectId) dbUser.get(ID);
		dbUser.set(EMAIL, email);
		dbUser.set(PASSWORD_HASH, passwordHash);
		dbUser.set(PASSWORD_SALT, passwordSalt);
		dbUser.set(CREATION_DATE, creationDate);
		dbUser.set(AUTH_TOKENS, authTokens);
		dbUser.set(FIRST_NAME, firstName);
		dbUser.set(LAST_NAME, lastName);
		dbUser.set(PHONE, phone);
		dbUser.set(ADDRESS, address);

		dbUser.setWriteMode(DBWriteMode.AUTOMATIC);
	}

	/**
	 * Loads all fields that would be used by getting them from the db
	 */
	protected void load(DBUser dbUser) {
		this.dbUser = dbUser;

		// load from database
		firstName = dbUser.getString(FIRST_NAME);
		lastName = dbUser.getString(LAST_NAME);
		email = dbUser.getString(EMAIL);
		phone = dbUser.getString(PHONE);
		address = dbUser.getList(ADDRESS, String.class);
		authTokens = dbUser.getList(AUTH_TOKENS, String.class);
	}

	// TODO: deleting accounts, consult with db developer
	public void deleteAccount() {
		dbUser.delete();
	}

	/**
	 * Generates a new authentication token when a user logs in and checks whether
	 * the token is unique. Adds the new token to the list of tokens, writes the
	 * list to the db and returns the new token
	 */
	public String logIn() {
		boolean isUnique = false;
		String newToken = null;
		while (!isUnique) {
			newToken = SecurityHelper.generateAuthKey();
			DBUser checkUser = new DBUser(DBUser.Id.AUTH_TOKEN, newToken);
			isUnique = !checkUser.doesExistInDB();
		}
		authTokens.add(newToken);
		dbUser.set(AUTH_TOKENS, authTokens);
		return newToken;
	}

	/**
	 * When a user logs out removes the token from the list and writes the changed
	 * list to the db
	 */
	public void logOut(String authToken) {
		authTokens.remove(authToken);
		dbUser.set(AUTH_TOKENS, authTokens);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		dbUser.set(FIRST_NAME, firstName);
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		dbUser.set(LAST_NAME, lastName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		dbUser.set(EMAIL, email);
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
		dbUser.set(PHONE, phone);
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
		dbUser.set(ADDRESS, address);
	}

	public List<String> getAuthTokens() {
		return authTokens;
	}

	public void setAuthTokens(List<String> authTokens) {
		this.authTokens = authTokens;
		dbUser.set(AUTH_TOKENS, authTokens);
	}

}
