package ac.brunel.techdon;

import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Student {
	@Id
	private String id;
	private String firstName;
	private String lastName;
	@Indexed(unique = true)
	private String email;
	private Role role;
	private String gender;
	private MainInfo mainInfo;
	private List<String> selectedDevices;
	private ZonedDateTime created;
	
}
