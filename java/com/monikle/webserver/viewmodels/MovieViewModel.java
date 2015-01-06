package com.monikle.webserver.viewmodels;

import com.monikle.memdb.RatingsTable;
import com.monikle.webserver.models.MovieDetail;

import java.util.Optional;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieViewModel {
	private static RatingsTable ratingsTable = RatingsTable.getTable();

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

		// Get movie rating from either the user or the estimate form the neural net
		Optional<Integer> rating = ratingsTable.getRating(username, movieId);
		if(rating.isPresent()) {
			this.isUserRating = true;
			this.rating = rating.get();
		} else {
			this.isUserRating = false;
			this.rating = estimateRating();
		}
	}

	private int estimateRating() {
		return 0; // TODO
	}
}
