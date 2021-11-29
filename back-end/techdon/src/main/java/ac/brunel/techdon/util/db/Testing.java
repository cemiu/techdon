package ac.brunel.techdon.util.db;

import ac.brunel.techdon.device.DeviceType;
import org.bson.Document;

import java.time.Instant;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.*;
import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

public class Testing {

    public static void main(String[] args) {

    }

    private static void loadDummyStudent() {
        DBStudent student = new DBStudent(DBUser.Id.EMAIL, "test@gmail.com");
        System.out.println(student.getString(FIRST_NAME));
        System.out.println(new DBStudent(DBUser.Id.AUTH_TOKEN, "FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8").getString(LAST_NAME));
    }

    private static void createDummyStudent() {
        DBStudent student = new DBStudent();
        student.set(EMAIL, "test@gmail.com");
        student.set(PASSWORD_HASH, "74101a325e6263b37f1152ea2139e87d718bf1b14aa5cbf8df9588d4e1befc39");
        student.set(PASSWORD_SALT, "b16824ea-8c76-4bc5-981a-ab9e2ac3ea47");
        student.set(CREATION_DATE, Instant.now().getEpochSecond());
        student.set(AUTH_TOKENS, Arrays.asList("FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8",
                "VEXFYG2J3K4M6P7Q8SATBUDEXFZH2J3M5N6P8R9SAUCVDWFYGZH2K4M5N7",
                "UCVDXFYGZH3K4M5P7Q8RATBUCWEXFYH2J3K5N6P7R9SATCVDWEXGZH2J4M"));
        student.set(FIRST_NAME, "Joe");
        student.set(LAST_NAME, "Bloggs");
        student.set(PHONE, "+44 151 496 0817");
        student.set(ADDRESS, Arrays.asList("Wilfred Brown Building",
                "Brunel University London",
                "Kingston Ln",
                "UB8 3PH",
                "Uxbridge"));

        student.set(UNIVERSITY, "Brunel University");
        student.set(COUNTRY, "United Kingdom");
        student.set(DEVICE_SELECTION, Arrays.asList(
                new Document("type", DeviceType.LAPTOP)
                        .append("date_selected", Instant.now().getEpochSecond()),
                new Document("type", DeviceType.HARD_DRIVE)
                        .append("date_selected", Instant.now().getEpochSecond()),
                new Document("type", DeviceType.MONITOR)
                        .append("date_selected", Instant.now().getEpochSecond())
        ));
        student.write();
    }

}
