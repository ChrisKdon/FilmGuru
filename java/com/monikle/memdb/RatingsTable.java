package com.monikle.memdb;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class RatingsTable {
	private static RatingsTable instance;

	private Map<String, Map<Integer, Integer>> userRatings; // <Username, <Movie ID, Rating>>
	private Map<String, Long> ratingModifications; 					// <Username, Modification Count>

	private RatingsTable() {
		this.userRatings = new HashMap<>();
		this.ratingModifications = new HashMap<>();
	}

	public synchronized static RatingsTable getTable() {
		if(instance == null) {
			instance = new RatingsTable();
		}

		return instance;
	}

	public synchronized void save(String username, int movieId, int rating) {
		Map<Integer, Integer> ratingForUser = userRatings.getOrDefault(username, new HashMap<>());
		ratingForUser.put(movieId, rating);
		userRatings.putIfAbsent(username, ratingForUser);

		ratingModifications.put(username, ratingModifications.getOrDefault(username, 0L) + 1);
	}

	/**
	 * Return the rating for a user and movie.
	 *
	 * @param username
	 * @param movieId
	 * @param defaultRating
	 * @return
	 */
	public synchronized int getOrDefault(String username, int movieId, int defaultRating) {
		return userRatings
				.getOrDefault(username, new HashMap<>())
				.getOrDefault(movieId, defaultRating);
	}

	public synchronized long modificationCount(String username) {
		return ratingModifications.getOrDefault(username, 0L);
	}

	public synchronized int recordCountFor(String username) {
		return userRatings.getOrDefault(username, new HashMap<>()).size();
	}
}
