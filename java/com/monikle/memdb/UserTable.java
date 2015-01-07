package com.monikle.memdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Table that stores user information for account management.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class UserTable {
	private static UserTable instance;

	private Map<String, String> users; // <Username, Password>

	private UserTable() {
		users = new HashMap<>();
	}

	public synchronized static UserTable getTable() {
		if (instance == null) {
			instance = new UserTable();
		}

		return instance;
	}

	public void save(String username, String password) {
		users.put(username, password);
	}

	/**
	 * Does the user exist in the database.
	 */
	public boolean exists(String username) {
		return users.containsKey(username);
	}

	/**
	 * Authenticate a user.
	 *
	 * @param username The username to use.
	 * @param password The password for the user.
	 * @return True if the username and password are correct.
	 */
	public boolean login(String username, String password) {
		return exists(username) && users.get(username).equals(password);
	}
}
