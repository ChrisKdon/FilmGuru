package com.monikle.memdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Singleton of the movie database.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieDatabase {
	private static MovieDatabase instance;

	private Map<String, Map<Integer, Integer>> userRatings; // <Username, <Movie ID, Rating>>

	private MovieDatabase() {
		this.userRatings = new HashMap<>();
	}

	public static MovieDatabase getDb() {
		if(instance == null) {
			instance = new MovieDatabase();
		}

		return instance;
	}

	public void setRating(String username, int movieId, int rating) {
		Map<Integer, Integer> ratingForUser = userRatings.getOrDefault(username, new HashMap<>());
		ratingForUser.put(movieId, rating);
		userRatings.putIfAbsent(username, ratingForUser);
	}

	public int getRatingOrDefault(String username, int movieId, int defaultRating) {
		return userRatings
				.getOrDefault(username, new HashMap<>())
				.getOrDefault(movieId, defaultRating);
	}
}
