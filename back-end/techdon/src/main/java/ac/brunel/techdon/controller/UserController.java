package ac.brunel.techdon.controller;

import ac.brunel.techdon.user.Donor;
import ac.brunel.techdon.user.Student;
import ac.brunel.techdon.user.User;
import ac.brunel.techdon.util.EmailHelper;
import ac.brunel.techdon.util.SecurityHelper;
import ac.brunel.techdon.util.db.DBUser;
import ac.brunel.techdon.util.db.fields.DBUserField;

import org.apache.naming.NamingEntry;
import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static ac.brunel.techdon.controller.util.ResponseHelper.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

	// TODO: finish up

	/**
	 * endpoint to register a student account
	 */
	@GetMapping("/api/user/register")
	public ResponseEntity<String> userRegister(
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam String email,
			@RequestParam String password,
			@RequestParam String phone,
			@RequestParam String userType,
			@RequestParam List<String> address,
			@RequestParam(required = false) String university
	) {
		if (!userType.equals("student") && !userType.equals("donor"))
			return BAD_REQUEST();

		// If the user is of type student and university is null return an error message
		if (userType.equals("student") && university == null) {
			return BAD_REQUEST("University is null");
		}



		// TODO: check for duplicates (email), check address


		// Checks if any of the parameters are equal to null or are empty and if so
		// returns a bad request error message in string format
		if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
				|| password.isEmpty() || phone.isEmpty()
				|| address.isEmpty() || address.size() > 5) {
			return BAD_REQUEST("One of the required fields is empty.");
		}


		// Checks whether the first name is between 1 and 25 characters, starts with a-z, contains
		// only letters from a to z,  the symbols  ('), (-), (,), (.) and ends only with a-z using regex
		if (!firstName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
			return BAD_REQUEST("Not a valid first name.");
		}


		// Checks whether the last name is between 1 and 25 characters, starts with a-z, contains
		// only letters from a to z,  the symbols  ('), (-), (,), (.) and ends only with a-z using regex
		if (!lastName.matches("(?i)(^[a-z])((?![ .,'-]$)[a-z .,'-]){0,24}$")) {
			return BAD_REQUEST("Not a valid last name.");
		}


		// Checks whether the email address is valid using regex
		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,20}$")) {
			return BAD_REQUEST("Not a valid email address.");
		}


		// Checks whether the phone number is a valid UK phone number using regex
		if (!phone.matches("^(((\\+44\\s?\\d{4}|\\(?0\\d{4}\\)?)\\s?\\d{3}\\s?\\d{3})|((\\+44\\s?\\d{3}|\\(?0\\d{3}\\)?)\\s?\\d{3}\\s?\\d{4})|((\\+44\\s?\\d{2}|\\(?0\\d{2}\\)?)\\s?\\d{4}\\s?\\d{4}))(\\s?\\#(\\d{4}|\\d{3}))?$")) {
			return BAD_REQUEST("Not a valid phone number.");
		}

		// Declares a variable of type User and depending on whether the user is of type student or donor
		// initializes it with the its constructor
		User user;
		if (userType.equals("student"))
			user = new Student(firstName, lastName, email, password, phone, address, university);
		else
			user = new Donor(firstName, lastName, email, password, phone, address);

		// Generates an auth token
		String auth = user.logIn();
		
		try {
			SimpleMailMessage mail = EmailHelper.getRegisterEmail(email, firstName);
			EmailHelper.sendEmail(mail);
		} catch (Exception e) {}

		// Returns a response of type Document as Json
		Document response = new Document("authToken", auth)
				.append("userType", userType);
		return OK(response.toJson());
	}

	/**
	 * endpoint for logging users in
	 */
	@GetMapping("/api/user/login")
	public ResponseEntity<String> logIn(
			@RequestParam String email,
			@RequestParam String password
	) {

		// Create a new database user and load their email
		DBUser dbUser = DBUser.loadUser(DBUser.Id.EMAIL, email);

		// Check whether it is valid
		if (dbUser == null) {
			return UNAUTHORIZED();
		}

		// Get the salt and hash
		String salt = dbUser.getString(PASSWORD_SALT);
		String newHash = SecurityHelper.getHash(password, salt);

		// Check if the new hash is the same as the one in db user
		if (!newHash.equals(dbUser.getString(PASSWORD_HASH))) {
			return UNAUTHORIZED();
		}

		// Generate the new authentication token and get the list of tokens
		String newAuthToken = SecurityHelper.generateAuthKey();
		List<String> authTokens = dbUser.getList(AUTH_TOKENS, String.class);

		// Add the new token to the list
		authTokens.add(newAuthToken);

		// Set and write to the database
		dbUser.set(AUTH_TOKENS, authTokens);
		dbUser.write();

		// Returns a response of type Document as Json
		Document response = new Document("authToken",  newAuthToken)
				.append("userType", dbUser.getString(USER_ROLE));
		return OK(response.toJson());
	}

	/**
	 * endpoint for logging users out
	 */
	@PostMapping("/api/user/logout")
	public ResponseEntity<String> logOut(@RequestParam String authToken) {
		// Create a new database user and load their authentication tokens
		DBUser dbUser = DBUser.loadUser(DBUser.Id.AUTH_TOKEN, authToken);

		// Check whether it is valid
		if (dbUser == null) {
			return UNAUTHORIZED();
		}

		// Get the list of tokens, assign them to a new variable and remove the specific token
		List<String> authTokens = dbUser.getList(AUTH_TOKENS, String.class);
		authTokens.remove(authToken);

		// Set and write to the database
		dbUser.set(AUTH_TOKENS, authTokens);
		dbUser.write();

		// Returns a new document as Json
		return OK();
	}

	/**
	 * endpoint for user's to get their settings
	 */
	@GetMapping("/api/user/account/settings/get")
	public ResponseEntity<String> getSettings(@RequestParam String authToken) {
		DBUser dbUser = DBUser.loadUser(DBUser.Id.AUTH_TOKEN, authToken);
		if (dbUser == null) {
			return UNAUTHORIZED();
		}

		Document response = new Document("userType", dbUser.getString(USER_ROLE))
				.append("firstName", dbUser.getString(FIRST_NAME))
				.append("lastName", dbUser.getString(LAST_NAME))
				.append("email", dbUser.getString(EMAIL))
				.append("phone", dbUser.getString(PHONE))
				.append("address", dbUser.getList(ADDRESS, String.class));
		if (dbUser.getString(USER_ROLE).equals("student"))
			response.append("university", dbUser.getString(UNIVERSITY));
		return OK(response.toJson());
	}

	/**
	 * endpoint for user's to update settings
	 */
	@PostMapping("/api/user/account/settings/update")
	public ResponseEntity<String> updateSettings(
			@RequestParam(required = false) String firstName,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String password,
			@RequestParam(required = false) String phone,
			@RequestParam(required = false) List<String> address,
			@RequestParam(required = false) String university,
			@RequestParam String authToken) {	
		
		DBUser dbUser = DBUser.loadUser(DBUser.Id.AUTH_TOKEN, authToken);
		
		if (dbUser == null) {
			return UNAUTHORIZED();
		}
		
		// Checks the validity and sets the new value in the database
		if (firstName != null && !firstName.isEmpty()) {
			dbUser.set(FIRST_NAME, firstName);
		}
		if (lastName != null && !lastName.isEmpty()) {
			dbUser.set(LAST_NAME, lastName);
		}
		if (email != null && !email.isEmpty()) {
			dbUser.set(EMAIL, email);
		}
		if (password != null && !password.isEmpty()) {
			// Get the salt and hash
			String salt = dbUser.getString(PASSWORD_SALT);
			String newHash = SecurityHelper.getHash(password, salt);
			
			dbUser.set(PASSWORD_HASH, newHash);
		}
		if (phone != null && !phone.isEmpty()) {
			dbUser.set(PHONE, phone);
		}
		if (address != null && !address.isEmpty()) {
			dbUser.set(ADDRESS, address);
		}
		if (university != null && !university.isEmpty()) {
			dbUser.set(UNIVERSITY.getKey(), university);
		}
		
		dbUser.write();
		
		return OK();
	}

	/**
	 * endpoint for deleting user accounts
	 */
	@DeleteMapping("/api/user/delete")
	public ResponseEntity<String> deleteAccount(@RequestParam String authToken) {
		DBUser dbUser = DBUser.loadUser(DBUser.Id.AUTH_TOKEN, authToken);
		
		if (dbUser == null) {
			return UNAUTHORIZED();
		}
		
		dbUser.delete();
		
		return OK();
	}
	
}
