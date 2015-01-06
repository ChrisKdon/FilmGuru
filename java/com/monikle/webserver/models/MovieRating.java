package com.monikle.webserver.models;

import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieRating {
	private MovieDetail movie;
	private Optional<Integer> rating;

	public MovieRating(MovieDetail movie, Optional<Integer> rating) {
		if(movie == null || rating == null) {
			throw new IllegalArgumentException("`movie` and `rating` can't be null.");
		}

		this.movie = movie;
		this.rating = rating;
	}

	public MovieDetail getMovie() {
		return movie;
	}

	public Optional<Integer> getRating() {
		return rating;
	}
}
