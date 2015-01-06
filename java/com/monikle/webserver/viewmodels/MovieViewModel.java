package com.monikle.webserver.viewmodels;

import com.monikle.webserver.models.MovieDetail;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieViewModel {
	private int movieId;            // ID of the movie on tmdb.org
	private String movieTitle;      // The name of the movie
	private String moviePosterUrl; 	// The movie poster URL
	private String imdbUrl;         // IMDB URL
	private int rating;							// Movie Rating
	private boolean isUserRating;		// True if the rating is one the user entered

	public MovieViewModel(String username, MovieDetail movie) {
		this.movieId = movie.getMovieId();
		this.movieTitle = movie.getMovieTitle();
		this.moviePosterUrl = "http://image.tmdb.org/t/p/w154" + movie.getMoviePosterFile();
		this.imdbUrl = "http://www.imdb.com/title/" + movie.getImdbId();

		// TODO: Calculate from NN
		this.rating = 3;
		this.isUserRating = false;
	}
}
