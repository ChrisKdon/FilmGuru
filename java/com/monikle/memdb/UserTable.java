package com.monikle.memdb;

import com.monikle.models.MovieDetail;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
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

	public boolean exists(String username) {
		return users.containsKey(username);
	}

	public boolean login(String username, String password) {
		return exists(username) && users.get(username).equals(password);
	}
}
