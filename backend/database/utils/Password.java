package database.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Password {
    /**
     * Generates a random salt for use in password hashing.
     *
     * @return a hexadecimal string representation of the generated salt
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return bytesToHex(salt);
    }

    /**
     * Hashes a password using SHA-512 with salt.
     *
     * @param password The password to be hashed.
     * @param salt     The salt to be used in the hashing process.
     * 
     * @return A hexadecimal string representation of the hashed password
     */
    public static String hash(String password, String salt) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            hash = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * Hashes a password using SHA-512 without a salt.
     *
     * @param password the password to be hashed
     * @return a hexadecimal string representation of the hashed password
     */
    public static String hash(String password) {
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());
            hash = bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    /**
     * Converts an array of bytes to a hexadecimal string.
     *
     * @param bytes the array of bytes to be converted
     * @return a hexadecimal string representation of the bytes
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
