package ac.brunel.techdon;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class StudentService {
	private final StudentRepository studentRepository;

	@GetMapping
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

}
