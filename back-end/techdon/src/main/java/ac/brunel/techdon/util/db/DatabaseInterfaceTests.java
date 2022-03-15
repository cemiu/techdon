package ac.brunel.techdon.util.db;

import ac.brunel.techdon.device.Device;
import ac.brunel.techdon.device.DevicePreference;
import ac.brunel.techdon.device.DeviceType;
import ac.brunel.techdon.util.SecurityHelper;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static ac.brunel.techdon.util.db.fields.DBUserField.*;
import static ac.brunel.techdon.util.db.fields.DBStudentField.*;

/**
 * This class contains some methods used to test
 * the functionality of the database interface
 * To be removed once interface development has finished
 */
@SuppressWarnings("unused")
public class DatabaseInterfaceTests {

    public static void main(String[] args) {
        createDummyDonor();
    }

    private static void addPreference() {
        DBStudent student = new DBStudent(DBUser.Id.AUTH_TOKEN, "FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8");
        DevicePreference preference = new DevicePreference(student.getId(), DeviceType.IPAD, true);

    }

    private static void loadPreference() {
        DBStudent student = new DBStudent(DBUser.Id.AUTH_TOKEN, "FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8");
        DevicePreference preference = new DevicePreference(student.getId(), DeviceType.IPAD, false);
        System.out.println("1: " + preference.getStudentId());
        System.out.println("2: " + preference.getDeviceType());
        System.out.println("3: " + DevicePreference.getPreferredDevicesByStudent(new ObjectId("61ab77b40be1bf0ad0193d99")));
        System.out.println("4: " + DevicePreference.getNextInQueueForDevice(DeviceType.IPAD));
        System.out.println("5: " + DevicePreference.getNextInQueueForDevice(DeviceType.LAPTOP));
    }

    private static void loadStringList() {
        DBStudent student = new DBStudent(DBUser.Id.AUTH_TOKEN, "FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8");
        List<String> auths = student.getList(AUTH_TOKENS, String.class);
        System.out.println(auths);
        System.out.println(auths.getClass());
        if (auths.size() > 0) {
            System.out.println(auths.get(0));
            System.out.println(auths.get(0).getClass());
        }
    }

    private static void loadAllDevices() {
        DBDonor donor = new DBDonor(DBUser.Id.AUTH_TOKEN, "R9SAUCVDWEYGZH2K4M5N7Q8R9TBUCVEXFYG2J3K4N6P7Q9SATBUDWEXFZH");
        System.out.println(donor.getDeviceList());
    }

    private static void addDevice() {
        Device device = new Device(new ObjectId(), DeviceType.IPAD, "iPad Air");
        device.setDescription("Good condition. For collection only, in Uxbridge");
        device.assignToStudent(new ObjectId());

    }

    private static void modifyDevice() {
        Device device = new Device("61b641f463ef9f620f9d8a21");
        System.out.println("1: " + device.getName());
        System.out.println("2: " + device.getDescription());
        System.out.println("3: " + device.isAssigned());
        System.out.println("4: " + device.getAssignedDate());

        device.unassign();
        device.removeDescription();

        System.out.println("5: " + device.getName());
        System.out.println("6: " + device.getDescription());
        System.out.println("7: " + device.isAssigned());
        System.out.println("8: " + device.getAssignedDate());
    }

    private static void loadDummyStudent() {
        DBStudent student = new DBStudent(DBUser.Id.EMAIL, "student@gmail.com");
        System.out.println(student.getString(FIRST_NAME));
        System.out.println(new DBStudent(DBUser.Id.AUTH_TOKEN, "FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8").getString(LAST_NAME));
        ObjectId id = (ObjectId) new DBStudent(DBUser.Id.EMAIL, "test@gmail.com").get(ID);
        System.out.println(id);
        System.out.println(id.toString());
        // System.out.println(id.equals("61a4340cbc906233499b7955")); -> contradiction
        System.out.println(id.equals(new ObjectId("61a4340cbc906233499b7955")));
        System.out.println(id.getDate());
    }

    private static void createDummyDonor() {
        DBDonor donor = new DBDonor();
        donor.set(EMAIL, "donor@gmail.com");

        String salt = UUID.randomUUID().toString();
        donor.set(PASSWORD_HASH, SecurityHelper.getHash("abc123", salt));
        donor.set(PASSWORD_SALT, salt);
        donor.set(CREATION_DATE, Instant.now().getEpochSecond());
        donor.set(AUTH_TOKENS, Arrays.asList("R9SAUCVDWEYGZH2K4M5N7Q8R9TBUCVEXFYG2J3K4N6P7Q9SATBUDWEXFZH", "R9SAUCVDWEYGZH2K4M5N7Q8R9TBUCVEXFYG2J3K4N6P7Q9SATBUDWEXFZI"));
        donor.set(FIRST_NAME, "Max");
        donor.set(LAST_NAME, "Abc");
        donor.set(PHONE, "+44 2467214156");
        donor.set(ADDRESS, Arrays.asList("Other Building",
                "Brunel University London",
                "Kingston Ln",
                "UB8 3PH",
                "Uxbridge"));
        donor.write();
    }

    private static void createDummyStudent() {
        DBStudent student = new DBStudent();
        student.set(EMAIL, "student@gmail.com");

        String salt = UUID.randomUUID().toString();
        student.set(PASSWORD_HASH, SecurityHelper.getHash("abc123", salt));
        student.set(PASSWORD_SALT, salt);

        student.set(CREATION_DATE, Instant.now().getEpochSecond());
        student.set(AUTH_TOKENS, Arrays.asList("FYH2J3K5N6P7R9SATBVDWEXGZH2J4M5N6Q8R9SBUCVDXFYGZJ3K4M6P7Q8",
                "VERIFY2J3K4M6P7Q8SATBUDEXFZH2J3M5N6P8R9SAUCVDWFYGZH2K4M5N7",
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
        
        student.write();
    }

}
