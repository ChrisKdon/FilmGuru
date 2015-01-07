package com.monikle.memdb;

import com.monikle.models.MovieRating;
import com.monikle.webserver.tmdb.MovieAPI;

import java.util.*;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class RatingsTable {
	private static Database db = Database.getDb();
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

	/**
	 * Save the rating for a particular user and movie.
	 *
	 * @param username The user to associate with the movie and rating.
	 * @param movieId The movie to associate with the rating.
	 * @param rating The rating for the movie.
	 */
	public synchronized void save(String username, int movieId, int rating) {
		Map<Integer, Integer> ratingForUser = userRatings.getOrDefault(username, new HashMap<>());
		ratingForUser.put(movieId, rating);
		userRatings.putIfAbsent(username, ratingForUser);

		ratingModifications.put(username, ratingModifications.getOrDefault(username, 0L) + 1);
	}

	/**
	 * Return the rating for a user and movie.
	 */
	public synchronized Optional<Integer> getRating(String username, int movieId) {
		int rating = userRatings
				.getOrDefault(username, new HashMap<>())
				.getOrDefault(movieId, -1);

		return rating == -1 ? Optional.empty() : Optional.of(rating);
	}

	/**
	 * Get all the movie ratings that a user has made.
	 */
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

	/**
	 * Get the number of times that a user has modified a rating.
	 */
	public synchronized long modificationCount(String username) {
		return ratingModifications.getOrDefault(username, 0L);
	}
}
