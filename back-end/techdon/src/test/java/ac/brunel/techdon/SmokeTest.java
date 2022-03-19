package ac.brunel.techdon;

import static org.assertj.core.api.Assertions.assertThat;

import ac.brunel.techdon.controller.DonorController;
import ac.brunel.techdon.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private StudentController studentController;
    @Autowired
    private DonorController donorController;
    @Autowired
    private StudentController UserController;



    @Test
    void studentController() {
        assertThat(studentController).isNotNull();

    }
    @Test
    void donorController() {
        assertThat(donorController).isNotNull();

    }
    @Test
    void userController() {
        assertThat(UserController).isNotNull();

    }
}