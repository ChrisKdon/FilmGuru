package com.monikle.memdb;

import com.monikle.webserver.models.MovieRating;
import com.monikle.webserver.tmdb.MovieAPI;

import java.util.*;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class RatingsTable {
	private static MovieDatabase db = MovieDatabase.getDb();
	private static RatingsTable instance;

	private Map<String, Map<Integer, Integer>> userRatings; // <Username, <Movie ID, Rating>>
	private Map<String, Long> ratingModifications;          // <Username, Modification Count>

	private RatingsTable() {
		this.userRatings = new HashMap<>();
		this.ratingModifications = new HashMap<>();
	}

	public synchronized static RatingsTable getTable() {
		if (instance == null) {
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
	 * @return
	 */
	public synchronized Optional<Integer> getRating(String username, int movieId) {
		int rating = userRatings
				.getOrDefault(username, new HashMap<>())
				.getOrDefault(movieId, -1);

		return rating == -1 ? Optional.empty() : Optional.of(rating);
	}

	public synchronized List<MovieRating> getMovieRatings(String username) throws Exception {
		List<MovieRating> movieRatings = new ArrayList<>();

		Map<Integer, Integer> moviesToRatings = userRatings.getOrDefault(username, new HashMap<>());

		for(int movieId : moviesToRatings.keySet()) {
			int rating = moviesToRatings.getOrDefault(movieId, -1);
			Optional<Integer> optRating = rating == -1 ? Optional.empty() : Optional.of(rating);
			movieRatings.add(new MovieRating(MovieAPI.movie(movieId), optRating));
		}

		return movieRatings;
	}

	public synchronized long modificationCount(String username) {
		return ratingModifications.getOrDefault(username, 0L);
	}

	public synchronized int recordCountFor(String username) {
		return userRatings.getOrDefault(username, new HashMap<>()).size();
	}
}
