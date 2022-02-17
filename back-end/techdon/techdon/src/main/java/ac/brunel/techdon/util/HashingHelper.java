package ac.brunel.techdon.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class HashingHelper {

    /**
     * Takes an unsalted, clear-text password and a salt
     * (preferably a UUIDv4) and returns a password that is salted
     * and hashed using SHA-256
     */
    public static String getHash(String password, String salt) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) { return null; }

        String saltedPW = salt + password;
        byte[] hashArray = digest.digest((saltedPW).getBytes());

        return bytesToHex(hashArray);
    }

    /**
     * Same as {@link #getHash(String, String)}, but takes
     * a salt of type {@link UUID} instead
     */
    public static String getHash(String password, UUID salt) {
        return getHash(password, salt.toString());
    }

    /**
     * Method converting a byte array to a hexadecimal formatted string.
     * Taken without modification from https://www.baeldung.com/sha-256-hashing-java
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
