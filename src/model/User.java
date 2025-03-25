
package model;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class User {
	private String username;
	private String hashedPwd;
	private String salt;
	private static final String DB = "Users.json";
	private static Map<String, User> users = new HashMap<>();

	/* CONSTRUCTORS */

	public User() {

	}

	public User(String username, String password) {
		this.username = username;
		this.salt = makeSalt();
		this.hashedPwd = hashPwd(password, this.salt);
	}

	/* Setters and Getters */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPwd() {
		return hashedPwd;
	}

	public void setHashedPwd(String hashedPwd) {
		this.hashedPwd = hashedPwd;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	// loadUsers() - load users from database json file
	private static void loadUsers() {
		File file = new File(DB);
		if (file.exists()) {
			try {
				ObjectMapper mapper = new ObjectMapper();
				users = mapper.readValue(file, new TypeReference<Map<String, User>>() {
				});
			} catch (IOException e) {
				System.err.println("error loading users from file" + e.getMessage());
			}

		}
	}

	// saveUser() - save user to json file database
	private static void saveUser() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(DB), users);
		} catch (IOException e) {
			System.err.println("error saving users to file" + e.getMessage());
		}
	}

	// isUser() - returns whether the user exists, creating a new user if they do
	// not and validating if they do
	public static boolean isUser(String username, String password) {
		if (users.isEmpty()) {
			loadUsers();
		}
		if (users.containsKey(username)) {
			return validateUser(username, password);
		} else {
			User user = new User(username, password);
			users.put(username, user);
			saveUser();
			return true;
		}

	}

	// validateUser() - validates password matches account
	public static boolean validateUser(String username, String password) {
		if (users.isEmpty()) {
			loadUsers();
		}
		if (users.containsKey(username)) {
			User user = users.get(username);
			String hashedSalted = user.hashPwd(password, user.salt);
			return hashedSalted.equals(user.hashedPwd);
		}
		return false;
	}

	// makeSalt() - generates a salt specific to a user
	public String makeSalt() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[16];
		random.nextBytes(bytes);
		return Base64.getEncoder().encodeToString(bytes);
	}

	// hashPwd() - hashes a password using message digest and adds salt
	public String hashPwd(String password, String salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(salt.getBytes(StandardCharsets.UTF_8));
			byte[] hashedPwd = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hashedPwd);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("error hashing password", e);
		}
	}

}
