package ac.brunel.techdon.util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

class DBPreferences {

    /**
     * TODO VERY IMPORTANT TO READ:
     * BEFORE FILLING IN THE CORRECT DATABASE INFORMATION THE FOLLOWING
     * COMMAND HAS TO BE EXECUTED IN THE PROJECT ROOT FOLDER:
     * git update-index --assume-unchanged back-end/techdon/src/main/java/ac/brunel/techdon/util/DBPreferences.java
     *
     * AFTER THE MODIFICATION, USE " git status " TO CONFIRM THAT THE CHANGES
     * WILL NOT BE STAGED
     */
    // MongoDB username & password, should not be committed
    private static final String MONGO_DB_USER = "INSERT_USERNAME_HERE";
    private static final String MONGO_DB_PASSWORD = "INSERT_PASSWORD_HERE";
    private static final String MONGO_DB_SERVER = "INSERT_SERVER_URI_HER";

    // MongoDB database name
    private static final String MONGO_DB_DB_NAME = "TechDon";

    // Complete connection URI, containing username & password; keep encapsulated
    private static final String MONGO_DB_CONNECTION_STRING =
            "mongodb+srv://" + MONGO_DB_USER + ":" + MONGO_DB_PASSWORD
                    + "@" + MONGO_DB_SERVER + "/" + MONGO_DB_DB_NAME
                    + "?retryWrites=true&w=majority";

    // Returns a <ongoClient instance
    public static MongoClient getClient() {
        return MongoClients.create(MONGO_DB_CONNECTION_STRING);
    }

}
