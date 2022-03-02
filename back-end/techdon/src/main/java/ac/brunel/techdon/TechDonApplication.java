package ac.brunel.techdon;

import ac.brunel.techdon.util.Preferences;
import ac.brunel.techdon.util.db.DBInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
	@GetMapping("/")
	public String root() {
		return "TechDon Back-End";
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}

}
