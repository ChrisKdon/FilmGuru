package com.monikle.webserver.models;

import com.monikle.memdb.MovieDatabase;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class MovieDetail {
	protected int movieId;            // ID of the movie on tmdb.org
	protected String movieTitle;      // The name of the movie
	protected String moviePosterFile; // The movie poster from http://image.tmdb.org/t/p/w500/<moviePosterFile>
	protected String imdbId;          // IMDB

	protected String[] genres;
	protected int year;
	protected boolean isEnglish;

	// Add empty constructor if object needs to be deserialized from gson

	public MovieDetail(int movieId, String movieTitle, String moviePosterFile,
										 String imdbId, String[] genres, int year, boolean isEnglish) {

		this.movieId = movieId;
		this.movieTitle = movieTitle;
		this.moviePosterFile = moviePosterFile;
		this.imdbId = imdbId;
		this.genres = genres;
		this.year = year;
		this.isEnglish = isEnglish;
	}

	public int getMovieId() {
		return movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public String getMoviePosterFile() {
		return moviePosterFile;
	}

	public String getImdbId() {
		return imdbId;
	}

	public String[] getGenres() {
		return genres;
	}

	public int getYear() {
		return year;
	}

	public boolean isEnglish() {
		return isEnglish;
	}
}
