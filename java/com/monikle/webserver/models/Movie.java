package com.monikle.webserver.models;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class Movie {
	private int movieId;					// ID of the movie on tmdb.org
	private String movieTitle;			// The name of the movie
	private String moviePosterFile; // The movie poster from http://image.tmdb.org/t/p/w500/<moviePosterFile>
	private int rating;							// 1 - 5 rating for the movie
	private boolean isUserRating;   // true if the rating comes from the user and not the neural net.

	public Movie() {}

	public Movie(int movieId, String movieTitle, String moviePosterFile, int rating, boolean isUserRating) {
		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.moviePosterFile = moviePosterFile;
		this.rating = rating;
		this.isUserRating = isUserRating;
	}
}
