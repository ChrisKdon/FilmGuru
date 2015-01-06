package com.monikle.webserver.models;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieRating {
	private MovieDetail movie;
	private int rating;

	public MovieRating(MovieDetail movie, int rating) {
		if(movie == null) {
			throw new IllegalArgumentException("`movie` can't be null.");
		}

		this.movie = movie;
		this.rating = rating;
	}

	public MovieDetail getMovie() {
		return movie;
	}

	public int getRating() {
		return rating;
	}
}
