package com.monikle.webserver.models;

import com.monikle.memdb.MovieDatabase;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public class BasicMovieDetail {
	protected static MovieDatabase db = MovieDatabase.getDb();

	protected int movieId;            // ID of the movie on tmdb.org
	protected String movieTitle;      // The name of the movie
	protected String moviePosterFile; // The movie poster from http://image.tmdb.org/t/p/w500/<moviePosterFile>
	protected int rating;              // 1 - 5 rating for the movie
	protected boolean isUserRating;   // true if the rating comes from the user and not the neural net.

	// Add empty constructor if object needs to be deserialized from gson

	public BasicMovieDetail(String username, int movieId, String movieTitle, String moviePosterFile) {
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.moviePosterFile = moviePosterFile;

		int rating = db.ratings.getOrDefault(username, movieId, -1);
		this.isUserRating = rating >= 0;

		if (!isUserRating) {
			// TODO: Calculate from neural network
		}

		this.rating = rating;
	}

	public int getMovieId() {
		return movieId;
	}
}
