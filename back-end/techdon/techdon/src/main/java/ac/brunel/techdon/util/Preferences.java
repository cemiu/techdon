package ac.brunel.techdon.util;

import org.bson.BsonDocumentReader;
import org.bson.Document;
import org.bson.json.JsonParseException;
import org.bson.json.JsonWriter;
import org.bson.json.JsonWriterSettings;

import java.io.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Class used to load read-only preferences
 * !!! When adding / adjusting preferences make sure to
 * update the template and increment the template version
 */
public class Preferences {

    private static final int PREF_VERSION = 1;

    private static final String APP_PATH = System.getProperty("user.home") + "/.techdon/";
    private static final String PREF_URI = APP_PATH + "pref.json";
    private static final File PREF_DIR = new File(APP_PATH);
    private static final File PREF_FILE = new File(PREF_URI);

    private static final Document PREFS;
    private static final Document DB_SETTINGS;

    // getters for mongodb connection / auth details
    public static String getDbUser() {
        return DB_SETTINGS.getString("mongodb_user");
    }

    public static String getDbPassword() {
        return DB_SETTINGS.getString("mongodb_password");
    }

    public static String getDbURI() {
        return DB_SETTINGS.getString("mongodb_server_uri");
    }

    public static String getDbName() {
        return DB_SETTINGS.getString("mongodb_db_name");
    }

    static {
        // creates template pref file, if none exists
        if (!PREF_FILE.exists()) {
            System.err.println("TechDon preference file not found!");
            System.err.println("Creating default file at: " + PREF_URI);

            try {
                // bson libraries can output documents as json, but do not format it
                // maybe include json library with formatting capabilities later (?)
                if (!PREF_DIR.exists())
                    PREF_DIR.mkdirs();
                BufferedWriter writer = new BufferedWriter(new FileWriter(PREF_FILE, StandardCharsets.UTF_8));
                writer.write("{\n" +
                        "\t\"pref_version\": " + PREF_VERSION + ",\n" +
                        "\t\"mongo_db_settings\": {\n" +
                        "\t\t\"mongodb_user\": \"INSERT USERNAME HERE\",\n" +
                        "\t\t\"mongodb_password\": \"INSERT PASSWORD HERE\",\n" +
                        "\t\t\"mongodb_server_uri\": \"INSERT SERVER URI HERE\",\n" +
                        "\t\t\"mongodb_db_name\": \"TechDon\"\n" +
                        "\t}\n" +
                        "}\n");
                writer.close();
            } catch (IOException e) {
                System.err.println("Failed to instantiate template preference file");
                e.printStackTrace();
                System.exit(1);
            }

            System.err.println("Please fill in the template preference file at: " +PREF_URI + ", and restart the server");
            System.exit(1);
        }

        // parses json from file
        String jsonString = null;
        try {
            jsonString = new String(Files.readAllBytes(Paths.get(PREF_URI)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println("Failed to read preference file at: " + PREF_URI);
            e.printStackTrace();
            System.exit(1);
        }

        Document prefTemp = null;
        try {
            prefTemp = Document.parse(jsonString);
        } catch (JsonParseException e) {
            System.err.println("Failed to parse preference file at: " + PREF_URI);
            System.err.println("Is it a valid JSON file?");
            e.printStackTrace();
            System.exit(1);
        }

        int fileVersion = prefTemp.getInteger("pref_version");
        if (PREF_VERSION > fileVersion) {
            System.err.println("The preference file at: " + PREF_URI + " is outdated!");
            System.err.println("The current preference version is " + PREF_VERSION + " while the loaded" +
                    " preferences are on version " + fileVersion + ".");
            System.err.println("Load the newest template by deleting the current file and restarting the server.");
            System.exit(1);
        }

        PREFS = prefTemp;
        DB_SETTINGS = (Document) PREFS.get("mongo_db_settings");
        System.out.println("[TechDon] Finished loading preference file from: " + PREF_URI);
    }

}
