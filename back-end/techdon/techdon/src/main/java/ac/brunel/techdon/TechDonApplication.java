package ac.brunel.techdon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TechDonApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechDonApplication.class, args);
	}

	@GetMapping("/")
	public String root() {
		return "TechDon Back-End";
	}

}
