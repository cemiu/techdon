package ac.brunel.techdon.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.bson.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ac.brunel.techdon.user.Donor;
import ac.brunel.techdon.user.Student;
import ac.brunel.techdon.user.User;
import ac.brunel.techdon.util.SecurityHelper;
import static ac.brunel.techdon.controller.util.ResponseHelper.*;
@CrossOrigin(origins="http://localhost:8080")
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
		
		// If the user is of type student and university is null return an error message
		if (userType == "student" && university == null) {
			return BAD_REQUEST("University is null");
		}
		
		
		// TODO: check for duplicates (email), check address
		
		
		// Checks if any of the parameters are equal to null or are empty and if so 
		// returns a bad request error message in string format	 
		if (firstName == "" || lastName == "" || email == "" 
				|| password == "" || phone == ""  || address.size() > 0) {
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
		
		
		// Returns a response of type Document as Json
		Document response = new Document("authToken", auth)
				.append("userType", userType);
		return OK(response.toJson());
	}
	
	/**
	 * endpoint to register a student account
	 */
	public String donorRegister(Donor donor) {
		
		String auth = SecurityHelper.generateAuthKey();
		donor = new Donor(auth);
		
		Document response = new Document("authToken", auth)
				.append("userType", "donor");
		return response.toJson();
	}
	
	/**
	 * endpoint for logging users in
	 */
	@GetMapping("/api/user/login")
	public void logIn(User user) {
		user.logIn();
	}
	
	/**
	 * endpoint for logging users out
	 */
	@GetMapping("/api/user/logout")
	public void logOut(User user) {	
		user.logOut(user.getAuthTokens().get(0));
	}
	
	/**
	 * endpoint for deleting user accounts
	 */
	@GetMapping("/api/user")
	public void deleteAccount(User user) {	
		user.deleteAccount();
	}
}
