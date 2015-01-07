package com.monikle.webserver.viewmodels;

import com.monikle.memdb.Database;
import com.monikle.models.MovieDetail;
import com.monikle.webserver.Config;
import com.monikle.webserver.rater.MovieRaterFactory;

import java.util.Optional;

/**
 * The movie details that are needed to be displayed to the user.
 *
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieViewModel {
	private static Database db = Database.getDb();

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
		Optional<Integer> rating = db.ratings.getRating(username, movieId);
		if(rating.isPresent()) {
			this.isUserRating = true;
			this.rating = rating.get();
		} else if(db.ratings.modificationCount(username) >= Config.UPDATE_NET_MODIFICATION_COUNT) {
			this.isUserRating = false;
			this.rating = estimateRating(username, movie);
		} else { // Not rated and the rating system hasn't been trained yet
			this.isUserRating = false;
			this.rating = 0;
		}
	}

	/**
	 * Calculate the estimated rating for a movie.
	 *
	 * @param username The username to get the rating system for.
	 * @param movie		 The movie to get the rating for.
	 * @return				 The rating for the movie.
	 */
	private static int estimateRating(String username, MovieDetail movie) {
		return MovieRaterFactory.getForUsername(username).getRating(movie);
	}
}
