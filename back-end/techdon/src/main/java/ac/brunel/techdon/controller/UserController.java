package ac.brunel.techdon.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ac.brunel.techdon.user.Donor;
import ac.brunel.techdon.user.Student;
import ac.brunel.techdon.user.User;

@RestController
public class UserController {
	
	// TODO: implement the registration endpoint so that it registers
	// as User not separately for students and donors
	
	/**
	 * endpoint to register a student account
	 */
	@GetMapping("/api/user/register")
	public Student studentRegister() {
		return new Student(null, null, null, 0, null, null, null, 
				null, null, null, null);
	}
	
	/**
	 * endpoint to register a donor account
	 */
	@GetMapping("/api/user/register")
	public Donor donorRegister() {
		return new Donor(null, null, null, 0, null, null, null, null, null);
	}
	
	/**
	 * endpoint for logging in users
	 */
	@GetMapping("/api/user/login")
	public void logIn(User user) {
		user.setLoggedIn(true);
	}
	
	/**
	 * endpoint for logging out users
	 */
	@GetMapping("/api/user/logout")
	public void logOut(User user) {
		user.setLoggedIn(false);
	}
}
