package ac.brunel.techdon.controller.util;

import org.bson.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHelper {

    public static ResponseEntity<String> UNAUTHORIZED() {
        return new ResponseEntity<>(new Document("error", "Failed authorization").toJson(), HttpStatus.UNAUTHORIZED);
    }

    public static ResponseEntity<String> BAD_REQUEST() {
        return BAD_REQUEST("Bad Request");
    }

    public static ResponseEntity<String> BAD_REQUEST(String msg) {
        return new ResponseEntity<>(new Document("error", msg).toJson(), HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<String> OK() {
        return OK(new Document("result", "ok").toJson());
    }

    public static ResponseEntity<String> OK_SIMPLE(String msg) {
        return OK(new Document("result", "ok").append("response", msg).toJson());
    }

    public static ResponseEntity<String> OK(String msg) {
        return new ResponseEntity<>(msg, HttpStatus.OK);
    }



}
