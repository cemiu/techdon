package ac.brunel.techdon.controller;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.brunel.techdon.user.Donor;
import ac.brunel.techdon.user.Student;
import ac.brunel.techdon.user.User;
import ac.brunel.techdon.util.SecurityHelper;

@RestController
public class UserController {
	
	// TODO: finish up
	
	/**
	 * endpoint to register a student account
	 */
	@GetMapping("/api/user/register")
	public String studentRegister(Student student) {
		
		String auth = SecurityHelper.generateAuthKey();
		student = new Student(auth);
		
		Document response = new Document("authToken", auth)
				.append("userType", "student");
		return response.toJson();
	}
	
	/**
	 * endpoint to register a student account
	 */
	@GetMapping("/api/user/register")
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
