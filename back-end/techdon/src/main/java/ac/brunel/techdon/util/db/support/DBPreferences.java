package ac.brunel.techdon.util.db.support;

import ac.brunel.techdon.util.Preferences;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class DBPreferences {

    // MongoDB username & password
    private static final String MONGO_DB_USER = Preferences.getDbUser();
    private static final String MONGO_DB_PASSWORD = Preferences.getDbPassword();
    private static final String MONGO_DB_SERVER = Preferences.getDbURI();

    // MongoDB database name
    private static final String MONGO_DB_DB_NAME = Preferences.getDbName();

    // Complete connection URI, containing username & password; keep encapsulated
    private static final String MONGO_DB_CONNECTION_STRING =
            "mongodb+srv://" + MONGO_DB_USER + ":" + MONGO_DB_PASSWORD
                    + "@" + MONGO_DB_SERVER + "/" + MONGO_DB_DB_NAME
                    + "?retryWrites=true&w=majority";

    // Returns a MongoClient instance
    public static MongoClient getClient() {
        return MongoClients.create(MONGO_DB_CONNECTION_STRING);
    }

}
