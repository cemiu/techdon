package ac.brunel.techdon;

import ac.brunel.techdon.util.Preferences;
import ac.brunel.techdon.util.db.DBInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TechDonApplication {

	public static void main(String[] args) throws ClassNotFoundException {
		// pre-launch initializations
		Class.forName(Preferences.class.getName());
		Class.forName(DBInterface.class.getName());

		// launches the application
		SpringApplication.run(TechDonApplication.class, args);
	}

}
