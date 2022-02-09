package ac.brunel.techdon.controller;

import org.bson.Document;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.brunel.techdon.user.Donor;
import ac.brunel.techdon.user.Student;
import ac.brunel.techdon.user.User;

@RestController
public class UserController {
	
	/**
	 * endpoint to register a student account
	 */
	@GetMapping("/api/user/register")
	public String studentRegister() {
		
		// TODO: finish up
		
		Document response = new Document("authToken", "dsgsggsgrsg")
				.append("userType", "student");
		return response.toJson();
	}
	
	/**
	 * endpoint for logging in users
	 */
	@GetMapping("/api/user/login")
	public void logIn(User user) {
		
	}
	
	/**
	 * endpoint for logging out users
	 */
	@GetMapping("/api/user/logout")
	public void logOut(User user) {
		
	}
}
