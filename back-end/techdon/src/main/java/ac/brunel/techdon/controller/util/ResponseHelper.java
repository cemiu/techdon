package ac.brunel.techdon.controller.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static ResponseEntity<String> UNAUTHORIZED() {
        return new ResponseEntity<>("Error: Failed authorization", HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> BAD_REQUEST() {
        return new ResponseEntity<>("Error: Bad Request", HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<String> BAD_REQUEST(String msg) {
        return new ResponseEntity<>("Error: " + msg, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<String> OK() {
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }

    public static ResponseEntity<String> OK(String msg) {
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

}
