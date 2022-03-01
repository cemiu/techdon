package ac.brunel.techdon.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;


public class SecurityHelper {
	private static final SecureRandom secureRandom = new SecureRandom();
	private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();
	
	/**
	 * Generates a secure random authentication token
	 * and returns it as base64 encoded string
	 */
	public static String generateAuthKey() {
		byte[] randomBytes = new byte[32];
	    secureRandom.nextBytes(randomBytes);
	    return base64Encoder.encodeToString(randomBytes);
	}

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
     * Taken with slight modification from https://www.baeldung.com/sha-256-hashing-java
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
