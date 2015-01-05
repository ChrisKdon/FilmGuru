package com.monikle.webserver.models;

import com.monikle.memdb.MovieDatabase;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Movie {
	private static MovieDatabase db = MovieDatabase.getDb();

	private int movieId;            // ID of the movie on tmdb.org
	private String movieTitle;      // The name of the movie
	private String moviePosterFile; // The movie poster from http://image.tmdb.org/t/p/w500/<moviePosterFile>
	private int rating;              // 1 - 5 rating for the movie
	private boolean isUserRating;   // true if the rating comes from the user and not the neural net.

	// Add empty constructor if object needs to be deserialized from gson

	private Movie(int movieId, String movieTitle, String moviePosterFile, int rating, boolean isUserRating) {
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.moviePosterFile = moviePosterFile;
		this.rating = rating;
		this.isUserRating = isUserRating;
	}

	public static Movie forUser(String username, int movieId, String movieTitle, String moviePosterFile) {
		int rating = db.getRatingOrDefault(username, movieId, -1);
		boolean hasUserRating = rating >= 0;

		if (!hasUserRating) {
			// TODO: Calculate from neural network
		}

		return new Movie(movieId, movieTitle, moviePosterFile, rating, hasUserRating);
	}
}
