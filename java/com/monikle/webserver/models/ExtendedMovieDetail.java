package com.monikle.webserver.models;

/**
 * Author:    Chris Kellendonk
 * Student #: 4810800
 */
public final class ExtendedMovieDetail extends BasicMovieDetail {
	protected String imdbUrl;

	protected transient String[] genres;
	protected transient int year;
	protected transient boolean isEnglish;

	public ExtendedMovieDetail(String username, int movieId, String movieTitle, String moviePosterFile,
														 String imdbId, String[] genres, int year, boolean isEnglish) {

		super(username, movieId, movieTitle, moviePosterFile);

		this.imdbUrl = "http://www.imdb.com/title/" + imdbId;
		this.genres = genres;
		this.year = year;
		this.isEnglish = isEnglish;
	}
}
